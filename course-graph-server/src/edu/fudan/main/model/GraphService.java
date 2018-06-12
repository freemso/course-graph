package edu.fudan.main.model;

import edu.fudan.main.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GraphService {

    private CourseRepository courseRepository;

}
