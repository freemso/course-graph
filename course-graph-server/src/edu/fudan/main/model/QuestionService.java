package edu.fudan.main.model;

import edu.fudan.main.domain.*;
import edu.fudan.main.dto.response.QuestionResp;
import edu.fudan.main.exception.NodeNotFoundException;
import edu.fudan.main.exception.PermissionDeniedException;
import edu.fudan.main.exception.QuestionNotFoundException;
import edu.fudan.main.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    private final NodeRepository nodeRepository;

    private final StudentRepository studentRepository;

    private final GraphRepository graphRepository;

    private final QuestionRepository questionRepository;

    private final QuestionMultipleChoiceRepository questionMultipleChoiceRepository;

    private final QuestionShortAnswerRepository questionShortAnswerRepository;

    private final ChoiceRepository choiceRepository;

    private final AnswerEntryRepository answerEntryRepository;

    private final PermissionService permissionService;

    public QuestionService(NodeRepository nodeRepository, StudentRepository studentRepository,
                           GraphRepository graphRepository, QuestionRepository questionRepository,
                           QuestionMultipleChoiceRepository questionMultipleChoiceRepository,
                           QuestionShortAnswerRepository questionShortAnswerRepository,
                           ChoiceRepository choiceRepository, AnswerEntryRepository answerEntryRepository,
                           PermissionService permissionService) {
        this.nodeRepository = nodeRepository;
        this.studentRepository = studentRepository;
        this.graphRepository = graphRepository;
        this.questionRepository = questionRepository;
        this.questionMultipleChoiceRepository = questionMultipleChoiceRepository;
        this.questionShortAnswerRepository = questionShortAnswerRepository;
        this.choiceRepository = choiceRepository;
        this.answerEntryRepository = answerEntryRepository;
        this.permissionService = permissionService;
    }

    /**
     * List all questions of the user.
     * If the user is the teacher, include statistical information in response.
     * If the user is a student, include his answers in response. For question he has not answered, keep it null.
     * Otherwise, raise PermissionDeniedException
     * @param currentUser, current login user
     * @param nodeId, id of the node
     * @return list of question information
     */
    public List<QuestionResp> getAllQuestionsOfNode(User currentUser, String nodeId) {
        // Node must exist
        // To reach the courseId we set the depth to 2
        Node node = nodeRepository.findById(nodeId, 2).orElseThrow(
                NodeNotFoundException::new
        );

        // Check read permission
        if (!permissionService.checkReadPermOfCourse(currentUser, node.getGraph().getCourse().getCourseId())) {
            throw new PermissionDeniedException();
        }

        // Get all questions of the node
        List<Question> questions = node.getQuestionList();

        List<QuestionResp> questionRespList = new ArrayList<>();

        for (Question shallowQuestion : questions) {
            // Get all data related to the question, set depth to -1
            Question question = questionRepository.findById(shallowQuestion.getQuestionId(), -1).orElseThrow(
                    QuestionNotFoundException::new
            );
            QuestionResp questionResp = new QuestionResp(question, currentUser);
            questionRespList.add(questionResp);
        }

        return questionRespList;
    }

    /**
     * create a new multiple-choice and add it to a course node
     *
     * @param nodeId      id of course node
     * @param description description of the question to be added
     * @param choices     choices of the question to be added
     * @param answer      the answer of this multiple-choice question
     * @return response information of the question created newly
     */
    public QuestionResp createQuestion(User currentUser, String nodeId, String description,
                                       QuestionType type, List<Choice> choices, String answer) {
        // Node must exist
        // To reach the courseId we set the depth to 2
        Node node = nodeRepository.findById(nodeId, 2).orElseThrow(
                NodeNotFoundException::new
        );

        long courseId = node.getGraph().getCourse().getCourseId();

        // Current user must have the write permission of the course
        if (!permissionService.checkWritePermOfCourse(currentUser, courseId)) {
            throw new PermissionDeniedException();
        }

        // Generate a new question id
        long questionId = RandomIdGenerator.getInstance().generateRandomLongId(questionRepository);

        Question question = null;

        switch (type) {
            case MULTIPLE_CHOICE: {
                question = new QuestionMultipleChoice(questionId, description, choices, answer, courseId);
                break;
            }
            case SHORT_ANSWER: {
                question = new QuestionShortAnswer(questionId, description, courseId);
                break;
            }
        }

        // Add the question to the node
        node.addQuestion(question);

        //save the changes in the database
        questionRepository.save(question);
        nodeRepository.save(node);

        // Return question response
        return new QuestionResp(question, currentUser);
    }

    /**
     * Student submit a answer
     * @param currentUser, current login user
     * @param questionId, id of the question
     * @param answer, content of the answer
     */
    public void createAnswerEntry(User currentUser, long questionId, String answer) {
        // Question must exist
        Question question = questionRepository.findById(questionId).orElseThrow(
                QuestionNotFoundException::new
        );

        // Current user must be the student of the question
        if (!permissionService.checkReadPermOfCourse(currentUser, question.getCourseId())) {
            throw new PermissionDeniedException();
        }

        AnswerEntry answerEntry = new AnswerEntry(currentUser.getId(), question, answer);

        answerEntryRepository.save(answerEntry);
    }

    private void deleteQuestion(Question question) {
        // Delete all the answer entries
        question.removeAnswers();
        questionRepository.save(question);

        if (question.getQuestionType() == QuestionType.MULTIPLE_CHOICE) {
            QuestionMultipleChoice questionMultipleChoice = questionMultipleChoiceRepository.findById(
                    question.getQuestionId()).orElseThrow(
                    QuestionNotFoundException::new
            );
            // Delete all the choices
            questionMultipleChoice.removeChoices();
            questionMultipleChoiceRepository.save(questionMultipleChoice);
        }

        questionRepository.delete(question);
    }

    /**
     * Delete a question.
     * For NodeService use.
     * Do NOT need to check authorization.
     * @param questionId id of question to be deleted
     */
    void deleteQuestion(long questionId) {
        // First question must exist
        Question question = questionRepository.findById(questionId, -1).orElseThrow(
                QuestionNotFoundException::new
        );

        deleteQuestion(question);
    }

    /**
     * Delete a question.
     * Need to check permission.
     * @param currentUser current login user
     * @param questionId if of the question
     */
    public void deleteQuestion(User currentUser, long questionId) {
        // First question must exist
        Question question = questionRepository.findById(questionId, -1).orElseThrow(
                QuestionNotFoundException::new
        );

        // Check if the login user is the owner/teacher of the course
        if (!permissionService.checkWritePermOfCourse(currentUser, question.getCourseId())) {
            throw new PermissionDeniedException();
        }

        deleteQuestion(question);
    }
}
