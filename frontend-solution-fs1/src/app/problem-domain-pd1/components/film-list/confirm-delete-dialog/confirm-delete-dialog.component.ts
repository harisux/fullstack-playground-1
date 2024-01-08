import { Component, inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-confirm-delete-dialog',
  templateUrl: './confirm-delete-dialog.component.html',
  styleUrls: ['./confirm-delete-dialog.component.scss']
})
export class ConfirmDeleteDialogComponent {

  //Deps
  dialogRef = inject(MatDialogRef<ConfirmDeleteDialogComponent>);
  dialogData = inject(MAT_DIALOG_DATA);

  closeDialog(confirmed: boolean): void {
    this.dialogRef.close(confirmed);
  }

}
