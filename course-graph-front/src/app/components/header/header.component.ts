import { Component } from '@angular/core';
import { StorageService } from '../../services/storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  curUser;

  constructor(private storage: StorageService) { }
  ngOnInit() {
    this.curUser = this.storage.getItem("curUser");
  }
}
