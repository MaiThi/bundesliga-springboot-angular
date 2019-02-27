import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {TeamsComponent} from './teams/teams.component';
import {PlayerAdminComponent} from './player-admin/player-admin.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {LoginComponent} from './login/login.component';
import {HeaderComponent} from './ui/header/header.component';
import {LayoutComponent} from './ui/layout/layout.component';
import {LayoutAdminComponent} from './ui/layout-admin/layout-admin.component';
import {ScheduleAdminComponent} from './schedule-admin/schedule-admin.component';
import {MatchesAdminComponent} from './matches-admin/matches-admin.component';
import {PlayersInMatchComponent} from './matches-admin/players-in-match/players-in-match.component';
import {MatchHappenedDetailsComponent} from './matches-admin/match-happened-details/match-happened-details.component';
import {TeamAnnonymousComponent} from './team-annonymous/team-annonymous.component';
import {ThreeStepForMatchDetailComponent} from './matches-admin/three-step-for-match-detail/three-step-for-match-detail.component';
import {ListUploadComponent} from './list-upload/list-upload.component';
import {FormUploadComponent} from './form-upload/form-upload.component';
import {TestAuthorityComponent} from './test-authority/test-authority.component';
import {UnauthorityComponent} from './unauthority/unauthority.component';
import {ScheduleAnnonymousComponent} from './schedule-annonymous/schedule-annonymous.component';
import {ShowMatchInfoAnnonymousComponent} from './show-match-info-annonymous/show-match-info-annonymous.component';
import {TableResultComponent} from './table-result/table-result.component';
import {ShowDetailsForTeamComponent} from './team-annonymous/show-details-for-team/show-details-for-team.component';

const appRoutes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: '',
        component: TeamAnnonymousComponent
      }
    ]
  },
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: 'tableResult',
        component: TableResultComponent
      }
    ]
  },
  {
    path: '',
    component: LayoutAdminComponent,
    children: [
      {
        path: 'teamAdmin',
        component: TeamsComponent
      }
    ]
  },
  {
    path: '',
    component: LayoutAdminComponent,
    children: [
      {
        path: 'playerAdmin',
        component: PlayerAdminComponent
      }
    ]
  },
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: 'schedule',
        component: ScheduleAnnonymousComponent
      }
    ]
  },
  {
    path: '',
    component: LayoutAdminComponent,
    children: [
      {
        path: 'scheduleAdmin',
        component: ScheduleAdminComponent
      }
    ]
  },
  {
    path: '',
    component: LayoutAdminComponent,
    children: [
      {
        path: 'matchAdmin',
        component: MatchesAdminComponent
      }
    ]
  },
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: 'showMatchInfoAnonymous/:id',
        component: ShowMatchInfoAnnonymousComponent
      }
    ]
  },
  {
    path: '',
    component: LayoutAdminComponent,
    children: [
      {
        path: 'combineMatchDetail/:id',
        component: ThreeStepForMatchDetailComponent
      }
    ]
  },
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: 'showDetailTeamTour/:id',
        component: ShowDetailsForTeamComponent
      }
    ]
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: 'unauthority',
        component: UnauthorityComponent
      }
    ]
  },
  {
    path: '**',
    component: LayoutComponent,
    children: [
      {
        path: '**',
        component: NotFoundComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
