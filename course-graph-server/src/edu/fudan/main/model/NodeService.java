package edu.fudan.main.model;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NodeService {



    public void deleteNode(long NodeId){

    }


    private void updateNodes(String jsMindData){
        JSONObject mindObject = new JSONObject(jsMindData);
        List<String> newNodeIds = new ArrayList<>();

    }


}
