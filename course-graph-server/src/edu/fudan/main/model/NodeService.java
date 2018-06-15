package edu.fudan.main.model;

import edu.fudan.main.domain.*;
import edu.fudan.main.dto.response.QuestionResp;
import edu.fudan.main.exception.GraphNotFoundException;
import edu.fudan.main.exception.NodeNotFoundException;
import edu.fudan.main.exception.PermissionDeniedException;
import edu.fudan.main.exception.QuestionNotFoundException;
import edu.fudan.main.repository.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class NodeService {


    private final NodeRepository nodeRepository;
    private final StudentRepository studentRepository;
    private final GraphRepository graphRepository;
    private final QuestionRepository questionRepository;
    private final QuestionMultipleChoiceRepository questionMultipleChoiceRepository;
    private final QuestionShortAnswerRepository questionShortAnswerRepository;
    private final ChoiceRepository choiceRepository;
    private final AnswerEntryRepository answerEntryRepository;


    public NodeService(NodeRepository nodeRepository,
                       StudentRepository studentRepository,
                       GraphRepository graphRepository,
                       QuestionRepository questionRepository,
                       QuestionMultipleChoiceRepository questionMultipleChoiceRepository,
                       QuestionShortAnswerRepository questionShortAnswerRepository,
                       ChoiceRepository choiceRepository,
                       AnswerEntryRepository answerEntryRepository) {
        this.studentRepository = studentRepository;
        this.nodeRepository = nodeRepository;
        this.graphRepository = graphRepository;
        this.questionRepository = questionRepository;
        this.questionMultipleChoiceRepository = questionMultipleChoiceRepository;
        this.questionShortAnswerRepository = questionShortAnswerRepository;
        this.choiceRepository = choiceRepository;
        this.answerEntryRepository = answerEntryRepository;
    }

    public void deleteNode(String nodeId) {
        //get course node
        Node node = nodeRepository.findById(nodeId).orElseThrow(
                NodeNotFoundException::new
        );

        //get all lists that the course node owns
        List<Question> questions = node.getQuestionList();
        List<Resource> resources = node.getResourceList();
        List<Lecture> lectures = node.getLectureList();

        //delete question list
        for(Question question:questions){
            this.deleteQuestion(question.getQuestionId());
        }
        //Todo: delete resources and lectures

        //delete the course node itself
        nodeRepository.delete(node);
    }

    /**
     * update all nodes related to one graph according to new jsmind data
     *
     * @param graphId    id of the graph
     * @param jsMindData new jsmind data
     */
    public void updateNodes(long graphId, String jsMindData) {
        //parse json string to json object
        JSONObject mindObject = new JSONObject(jsMindData);
        JSONObject mindData = (JSONObject) mindObject.get("data");

        //get existing nodes related to the graph
        Graph graph = graphRepository.findById(graphId).orElseThrow(
                GraphNotFoundException::new
        );
        Set<Node> oldNodes = graph.getNodeSet();

        //update old nodes and create new nodes
        Set<Node> newNodes = new HashSet<>();
        getNodesFromMindData(mindData, newNodes);
        //save() will merge new node with old node if it existed before, otherwise add it to the database
        nodeRepository.saveAll(newNodes);
        graph.setNodeSet(newNodes);
        graphRepository.save(graph);

        //find deleted nodes and delete them from database
        Set<String> oldIds = new HashSet<>(), newIds = new HashSet<>();
        for(Node node: oldNodes)
            oldIds.add(node.getNodeId());
        for(Node node: newNodes)
            newIds.add(node.getNodeId());
        oldIds.removeAll(newIds);
        for(String nodeId: oldIds)
            deleteNode(nodeId);
//        oldNodes.removeAll(newNodes);
//        if(oldNodes == null)
//            return;
//        for(Node node:oldNodes){
//            deleteNode(node.getNodeId());
//        }



    }

    /**
     * get all nodes in this mind-map
     *
     * @param mindNode the root of the mind-map json object
     * @param newNodes a list to save all nodes in this mind-map
     */
    private void getNodesFromMindData(JSONObject mindNode, Set<Node> newNodes) {
        Node node = new Node((String) mindNode.get("id"), (String) mindNode.get("topic"));
        newNodes.add(node);
        if (mindNode.has("children")) {
            JSONArray children = (JSONArray)mindNode.get("children");
            for(int i = 0; i < children.length(); i++){
                getNodesFromMindData((JSONObject) children.get(i), newNodes);
            }
        }
    }

    /**
     * list all questions of the user
     *
     * @param currentUser
     * @param nodeId
     * @return list of question information
     */
    public List<QuestionResp> getAllQuestionsOfNode(User currentUser, String nodeId) {
        //get course node
        Node node = nodeRepository.findById(nodeId).orElseThrow(
                NodeNotFoundException::new
        );

        //get all questions related to the node
        List<Question> questions = node.getQuestionList();

        //generate question response
        List<QuestionResp> questionRespList = new ArrayList<>();
        for (Question q : questions) {
            QuestionResp questionResp = new QuestionResp(q, currentUser.getType());
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
    public QuestionResp addQuestionMultipleChoiceOfNode(String nodeId, String description, List<Choice> choices, String answer) {
        //get the course node
        Node node = nodeRepository.findById(nodeId).orElseThrow(
                NodeNotFoundException::new
        );

        //create a new multiple-choice question
        Question question = new QuestionMultipleChoice(RandomIdGenerator.getInstance().generateRandomLongId(questionRepository),
                description, choices, answer);

        //add it to the node
        node.addQuestion(question);

        //save the changes in the database
        nodeRepository.save(node, 0);
        questionMultipleChoiceRepository.save((QuestionMultipleChoice) question);
        //questionRepository.save(question, 0);

        //return question response
        return new QuestionResp(question, UserType.TEACHER);
    }

    /**
     * create a new short-answer and add it to a course node
     *
     * @param nodeId      id of the course node
     * @param description description of the question
     * @return response information of the question created newly
     */
    public QuestionResp addQuestionShortAnswerOfNode(String nodeId, String description) {
        //get the course node
        Node node = nodeRepository.findById(nodeId).orElseThrow(
                NodeNotFoundException::new
        );

        //create a new short-answer question
        Question question = new QuestionShortAnswer(RandomIdGenerator.getInstance().generateRandomLongId(questionRepository),
                description);

        //add it to the node
        node.addQuestion(question);

        //save the changes in the database
        nodeRepository.save(node, 0);
//        questionRepository.save(question, 0);
        questionShortAnswerRepository.save((QuestionShortAnswer) question);

        //return question response
        return new QuestionResp(question, UserType.TEACHER);
    }

    /**
     * handle user's answers of some questions, return the results(right or wrong)
     *
     * @param currentUser current user who submits his answers
     * @param submissions the questions that user answered
     * @return a list of results (right or wrong)
     */
    public List<AnswerResult> handleQuestionSubmission(User currentUser, List<QuestionSubmission> submissions) {
        //if the user is teacher, refuse this operation
        if (currentUser.getType().equals(UserType.TEACHER))
            throw new PermissionDeniedException();

        //if the user is student, get this student
        Student student = studentRepository.findById(currentUser.getId()).get();

        List<AnswerResult> answerResults = new ArrayList<>();
        for (QuestionSubmission questionSubmission : submissions) {

            //get submission's question id and answer
            long questionId = questionSubmission.getQuestionId();
            String submittedAnswer = questionSubmission.getAnswer();

            //for multiple-choice question
            if (questionMultipleChoiceRepository.existsById(questionId)) {
                //get this multiple-choice question
                QuestionMultipleChoice question = questionMultipleChoiceRepository.findById(questionId).get();

                //check whether the answer submitted is right
                boolean isRight = submittedAnswer.equalsIgnoreCase(question.getCorrectAnswerKey());

                //add a new answer entry
                AnswerEntryMultipleChoice answerEntry = new AnswerEntryMultipleChoice(
                        RandomIdGenerator.getInstance().generateRandomLongId(answerEntryRepository),
                        student, submittedAnswer, question, isRight
                );

                //save in the database
                answerEntryRepository.save(answerEntry);

                //add the result in answer results;
                answerResults.add(new AnswerResult(questionId, isRight));

            }
            //for short-answer question
            else if (questionShortAnswerRepository.existsById(questionId)) {
                //get this short-answer question
                QuestionShortAnswer question = questionShortAnswerRepository.findById(questionId).get();

                //add a new answer entry
                AnswerEntryShortAnswer answerEntry = new AnswerEntryShortAnswer(
                        RandomIdGenerator.getInstance().generateRandomLongId(answerEntryRepository),
                        student, submittedAnswer, question
                );

                //save in the database
                answerEntryRepository.save(answerEntry);
            }
        }
        return answerResults;
    }

    /**
     * delete a question
     *
     * @param questionId id of question to be deleted
     */
    public void deleteQuestion(long questionId) {
        // if the question is a short question
        if (questionShortAnswerRepository.existsById(questionId)) {
            QuestionShortAnswer question = questionShortAnswerRepository.findById(questionId).get();

            //delete all answer entry
            for (AnswerEntry answerEntry : question.getAnswerEntryList())
                answerEntryRepository.delete(answerEntry);

            //delete this question
            questionShortAnswerRepository.delete(question);

        } else if (questionMultipleChoiceRepository.existsById(questionId)) {
            QuestionMultipleChoice question = questionMultipleChoiceRepository.findById(questionId).get();

            //delete all choices
            for (Choice choice : question.getChoices()) {
                choiceRepository.delete(choice);
            }

            //delete all answer entry
            for (AnswerEntry answerEntry : question.getAnswerEntryList()) {
                answerEntryRepository.delete(answerEntry);
            }

            //delete this question
            questionMultipleChoiceRepository.delete(question);
        } else
            // Not find this question, throw exception
            throw new QuestionNotFoundException(questionId);


    }

    public void addResourceOfNode() {

    }

    public void getAllResourcesOfNode() {

    }

    public void deleteResource() {

    }

    public void addLecture() {

    }

    public void getAllLecturesOfNode() {

    }

    public void deleteLecture() {

    }


}
