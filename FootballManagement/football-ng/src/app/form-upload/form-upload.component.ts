import { Component, OnInit } from '@angular/core';
import {UploadFileService} from '../ApiService/upload-file.service';
import {HttpEventType, HttpResponse} from '@angular/common/http';

// @ts-ignore
@Component({
  selector: 'app-form-upload',
  templateUrl: './form-upload.component.html',
  styleUrls: ['./form-upload.component.css']
})
export class FormUploadComponent implements OnInit {
  selectedFiles: FileList
  currentFileUpload: File

  constructor(private uploadService: UploadFileService) { }

  ngOnInit() {
  }

  upload() {
    this.currentFileUpload = this.selectedFiles.item(0);
    this.uploadService.pushFileToStorage(this.currentFileUpload).subscribe(
      res => {
        alert(res);
      }
    );
  }
}
