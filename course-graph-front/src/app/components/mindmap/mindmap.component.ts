import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { StorageService } from '../../services/storage.service';
// import {} from 'jsmind';

// 引入jsmind.js文件
import jsMind from 'jsmind/js/jsmind';
import '../../../assets/jsmind.screenshot.js';
import '../../../assets/jsmind.draggable.js';
import { GraphService } from '../../services/graph.service';
// jsMind的设置参数
const options = {
  container: 'jsmind_container',
  theme: 'greensea',
  editable: true
};
// 思维导图Mindmap渲染的json文件
let graphData: { [key: string]: object; } = {};

@Component({
  selector: 'app-mindmap',
  templateUrl: './mindmap.component.html',
  styleUrls: ['./mindmap.component.css']
})

export class MindmapComponent implements OnInit {
  @Input() curNodeId = -1;
  @Output() change: EventEmitter<number> = new EventEmitter<number>();
  curUser;
  currentGraphID;
  node_selected;


  public title = 'mindmap';
  // mindMap;
  public jm;
  public fgColor = "#ffffff";
  public bgColor = "#000000";


  constructor(
    private graphService: GraphService,
    private storage: StorageService
  ) {
  }


  //初始化
  ngOnInit() {
    this.node_selected = false;
    this.curUser = this.storage.getItem("curUser");
    this.jm = new jsMind(options);
    if (this.curUser.type == 'STUDENT') {
      this.jm.disable_edit();
    }
  }

  //移除节点
  removeNode() {
    const selected_id = this.jm.get_selected_node();
    if (!selected_id) {
      alert("请先选中节点");
      return;
    }
    this.jm.remove_node(selected_id);
  }

  //增加节点
  addNode() {
    const selected_node = this.jm.get_selected_node(); // as parent of new node
    if (!selected_node) {
      alert("请先选中节点");
      return;
    }
    const nodeid = jsMind.util.uuid.newid();
    const topic = '* Node_' + nodeid.substr(0, 5) + ' *';
    const node = this.jm.add_node(selected_node, nodeid, topic);
  }

  //打印图片
  prtScn() {
    this.jm.screenshot.shootDownload();
  }

  //创建思维导图
  createGraph(id) {
    this.currentGraphID = id;
    console.log("create");
    const rootId = "root" + jsMind.util.uuid.newid();

    const newmindda = {
      "meta": {
        "name": "jsMind remote",
        "author": "hizzgdev@163.com",
        "version": "0.2"
      },
      "format": "node_tree",
      "data": {
        "id": rootId, "topic": "根目录", "children": []
      }
    };
    graphData[id] = newmindda;
    this.jm.show(newmindda);
  }

  //改变节点背景颜色
  changeColor() {
    // console.log(this.color);
    const select_node = this.jm.get_selected_node();
    if (null == select_node) {
      alert("请先选中节点");
      return;
    }
    this.jm.set_node_color(select_node.id, this.bgColor, null);
  }

  //改变字体颜色
  changeFontColor() {
    // console.log(this.color);
    const select_node = this.jm.get_selected_node();
    if (null == select_node) {
      alert("请先选中节点");
      return;
    }
    this.jm.set_node_color(select_node.id, null, this.fgColor);
  }

  //查看节点是否被选中
  checkStatus() {
    const select_node = this.jm.get_selected_node();
    if (null == select_node) {
      this.node_selected = false;
      this.curNodeId = null;
      console.log("nothing");
    } else {
      this.node_selected = true;
      this.curNodeId = select_node.id;
      console.log(select_node);
    }
    this.change.emit(this.curNodeId);
  }

  //保存思维导图
  save() {
      const data = jsMind.format.node_tree.get_data(this.jm.mind);
      graphData[this.currentGraphID] = data;
      console.log("save graph:");

      let body = {"jsmind": JSON.stringify(data)};

      let _that = this;
      this.graphService.updateGraphData(this.currentGraphID, body).subscribe(function (suc) {
        let sucResp = JSON.parse(suc['_body']);
        console.log("save graph resp");
        console.log(sucResp);
        // alert("已保存当前思维导图");
        return true;
      }, function (err) {
        let errResp = JSON.parse(err['_body']);
        console.log(errResp);
        // alert("当前思维导图保存失败");
        return false;
      });
  }

  //clear
  clear() {
    this.jm._reset();
  }

  //根据graphID切换思维导图
  getData(graphId) {
    this.currentGraphID = graphId;

    if (graphData[graphId] == null) {

      console.log("get jsmind");
      let _that = this;
      this.graphService.getGraphData(graphId).subscribe(function (suc) {
        let sucResp = JSON.parse(suc['_body']);
        console.log("get jsmind resp");
        console.log(sucResp);

        graphData[graphId] = JSON.parse(sucResp.jsmind);

        _that.jm.show(graphData[graphId]);
      }, function (err) {
        let errResp = JSON.parse(err['_body']);
        console.log(errResp);
        alert(errResp.message);
      });
    } else {
      this.jm.show(graphData[graphId]);
    }
  }
}
