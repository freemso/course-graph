package edu.fudan.main.model;

import edu.fudan.main.domain.Node;
import edu.fudan.main.repository.NodeRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Service
public class NodeService {


    private final NodeRepository nodeRepository;

    public NodeService(NodeRepository nodeRepository){
        this.nodeRepository = nodeRepository;
    }

    public void deleteNode(String NodeId){
        
    }


    public  void updateNodes(String jsMindData){
        JSONObject mindObject = new JSONObject(jsMindData);
        JSONObject mindData = (JSONObject)mindObject.get("data");
        List<Node> newNodes =  new ArrayList<>();
        List<Node> oldNodes = (List<Node>)nodeRepository.findAll(0);
        /*
        1. update old nodes and add new nodes when retrieving the mind-map
        2. save all node ids during the retrieving process
        3. find the nodes deleted and delete them.
         */
        updateNodesFromMindData(mindData, newNodes);
        for(Node node: oldNodes){
            if(!newNodes.contains(node.getNodeId())){
               this.deleteNode(node.getNodeId());
            }
        }
    }

    private void updateNodesFromMindData(JSONObject mindNode, List<Node> newNodes){
        Node node = new Node((String)mindNode.get("id"), (String)mindNode.get("topic"));
       // nodeRepository.save(node, 0);
        newNodes.add(node);
        if(mindNode.has("children")){
            for(JSONObject subNode : (JSONObject[]) mindNode.get("children")){
                updateNodesFromMindData(subNode, newNodes );
            }
        }
        return;
    }


}
