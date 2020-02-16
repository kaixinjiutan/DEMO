import {
  Component,
  OnInit,
  NgModule,
  ViewChild,
  ChangeDetectorRef
} from '@angular/core';
import { MMaterialModule } from '../../home/material-module';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { SharedModule } from '../../shared/shared.module';
import { UserElement } from '../user';
import { UserAddComponent } from '../user-add/user-add.component';
import { MatDialog } from '@angular/material/dialog';
import { UserService } from '../user.service';
import { MatSort, MatSnackBar } from '@angular/material';
import { Router } from '@angular/router';
import {
  FormBuilder,
  FormGroup,
} from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';


@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
@NgModule({
  declarations: [],
  imports: [MMaterialModule, SharedModule],
  bootstrap: [],
  exports: [MMaterialModule, SharedModule]
})
export class UserListComponent implements OnInit {
  listUsers: UserElement[] = [];
  inputUsers: UserElement[] = [];
  filteredInputUsers: Observable<UserElement[]>;
  dataSource = new MatTableDataSource<UserElement>();
  inputUserForm: FormGroup = this.fb.group({
    inputUser: ''
  });
  displayedColumns: string[] = [
    'id',
    'userCode',
    'password',
     'status',
    'operator'
  ];

  @ViewChild(MatPaginator, null) paginator: MatPaginator;
  @ViewChild(MatSort, null) sort: MatSort;

  constructor(
    private ref: ChangeDetectorRef,
    private dialog: MatDialog,
    private userService: UserService,
    private router: Router,
    public fb: FormBuilder,
    public snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    this.getUsers();
    this.displayUserList();
  }

  private filteredInputUser(name: string) {
    return this.inputUsers.filter(
      inputUser => inputUser.userCode.indexOf(name) === 0
    );
  }

  //input获取焦点的时候列出所有的用户
  displayUserList() {
    console.log('this.inputUserForm.controls.inputUser.valueChanges');
    console.log(this.inputUserForm.controls.inputUser.valueChanges);
    this.filteredInputUsers = this.inputUserForm.controls.inputUser.valueChanges.pipe(
      startWith<string | UserElement>(''),
      map(value => (typeof value === 'string' ? value : value.userCode)),
      map(name =>
        name ? this.filteredInputUser(name) : this.inputUsers.slice()
      )
    );
  }
  displayFn(inputUser?): string | undefined {

    return inputUser ? inputUser.userCode : undefined;
  }

  initMatTable() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
  // 删除数据
  deleteUser(user: UserElement) {
    if (user && user.id > 0) {
      let sucessful = false;
      this.userService.deletUser(user.id).subscribe(
        (record: number) => {
          if (record > 0) {
            sucessful = true;
            this.getUsers();
          }
          this.openSnackBar(sucessful);
        },
        (error: any) => {
          console.log(error);
        }
      );
    }
  }
  openSnackBar(sucessful: boolean) {
    if (sucessful) {
      this.snackBar.open('用户数据', '删除成功', { duration: 3000 });
      // this.dialogRef.close(this.user);
    } else {
      this.snackBar.open('用户数据', '删除失败', { duration: 3000 });
    }
  }
  searchList(): void {
    const name = this.inputUserForm.value.inputUser.userCode;
    let tableData: UserElement[] = [];
    if (name && name.length > 0) {
      tableData = this.listUsers.filter(
        listUser => listUser.userCode.indexOf(name) === 0
      );
    } else {
      tableData = this.listUsers;
    }
    this.dataSource = new MatTableDataSource<UserElement>(tableData);
    this.initMatTable();
  }

  addUser(): void {
    this.openEditUserDialog(new UserElement(0, '', '', 0));
  }

  updateUser(user: UserElement): void {
    this.openEditUserDialog(user);
  }

  private openEditUserDialog(data: UserElement): void {
    const dialogRef = this.dialog.open(UserAddComponent, {
      width: '60%',
      data: data
    });

    dialogRef.afterClosed().subscribe(result => {
      this.getUsers();
    });
  }
  // 获取用户列表
  getUsers(): void {
    this.userService.getUsers().subscribe(
      (data: UserElement[]) => {
        this.listUsers = data;
        this.inputUsers = this.listUsers;
        this.dataSource = new MatTableDataSource<UserElement>(this.listUsers);
        this.initMatTable();
      },
      (error: any) => {
        console.log(error);
      }
    );
  }
  // 当输入框中有输入信息时，列出相应的用户信息
  getSearchUsers(): void {
    let tableData = [];
    const name = this.inputUserForm.value.inputUser;
    // 获取input输入的值
    // 在用户列表中找到关键值的数据，遍历出来
    for (let i = 0; i < this.listUsers.length; i++) {
      if (this.listUsers[i].userCode.indexOf(name) != -1) {
        tableData.push(this.listUsers[i]);
      }
    }
    this.dataSource = new MatTableDataSource<UserElement>(tableData);
    this.initMatTable();
  }
}
