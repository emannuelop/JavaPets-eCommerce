import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDrawer } from '@angular/material/sidenav';
import { SidebarService } from '../../services/sidebar.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  @ViewChild('drawer') public drawer!: MatDrawer;

  constructor(private sideBarService: SidebarService) {  }

  ngOnInit(): void {
      this.sideBarService.sideNavToggleSubject.subscribe(
        () => {
          this.drawer.toggle();
        }
      )
  }
}
