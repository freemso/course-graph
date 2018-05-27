package main.java.model;

import neo4j.repositories.CourseModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class CourseModuleService {

    private final CourseModuleRepository courseModuleRepository;

    public CourseModuleService(CourseModuleRepository courseModuleRepository){
        this.courseModuleRepository = courseModuleRepository;
    }

    @Transactional(readOnly = true)
    public Map getCourseGraph(long id){
        LinkedHashMap rawData = courseModuleRepository.fetchCourseModuleTreeByRootId(id);
        //recurisely modify hashmap
       // LinkedHashMap modifiedDate = new LinkedHashMap();
        return modifyCourseGraphMap(rawData);
    }


    private Map modifyCourseGraphMap(Map rawData) {
        Map modifiedData = new LinkedHashMap();
        for (Object key : rawData.keySet())
            if (((String) key).equals("_id"))
                modifiedData.put("id", String.valueOf(rawData.get(key)));
            else if (((String) key).equals("topic"))
                modifiedData.put((String) key, (String) rawData.get(key));
            else if (((String) key).equals("children.direction"))
                modifiedData.put("direction", rawData.get(key));
            else if (((String) key).equals("children")) {
                ArrayList<Map> children = (ArrayList) rawData.get(key);
                ArrayList<Map> modifiedChildren = new ArrayList<>();
                for (Map child : children
                        ) {
                    modifiedChildren.add(modifyCourseGraphMap(child));
                }
                modifiedData.put("children", modifiedChildren);
            }
        return modifiedData;
    }
}
