import {Injectable} from '@angular/core';
import {BsModalService} from 'ngx-bootstrap/modal';
import {BsModalRef} from 'ngx-bootstrap/modal/bs-modal-ref.service';

@Injectable({
    providedIn: 'root'
})

export class AlertService {

    template:
`
  <ng-template>
    <div class="modal-header">
      <h4 class="modal-title pull-left">错误</h4>
      <button type="button" class="close pull-right" aria-label="Close" (click)="modalRef.hide()">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
      {{message}}
    </div>
  </ng-template>`

    modalRef: BsModalRef;

    config = {
        backdrop: true,
        ignoreBackdropClick: false
    };

    message = "lalala";

    constructor(private modalService: BsModalService) {
    }

    showAlert(message: string) {
        this.message = message;
        this.modalRef = this.modalService.show(this.template, this.config);
        alert(this.message);
    }
}
