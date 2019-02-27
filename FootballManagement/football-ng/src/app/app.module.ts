import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './ui/header/header.component';
import { FooterComponent } from './ui/footer/footer.component';
import { HeaderAdministratorComponent } from './ui/header-administrator/header-administrator.component';
import { TeamsComponent } from './teams/teams.component';
import { TeamComponent } from './teams/team/team.component';
import { SeasonComponent } from './teams/season/season.component';
import {Router, RouterModule, Routes} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { TeamsInfoComponent } from './teams/teams-info/teams-info.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { PlayerAdminComponent } from './player-admin/player-admin.component';
import { PlayersInfoComponent } from './player-admin/players-info/players-info.component';
import { MaterialModule } from './material/material.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { PlayerComponent } from './player-admin/player/player.component';
import {ReactiveFormsModule } from '@angular/forms';
import { PlayerTeamSeasonComponent } from './player-admin/player-team-season/player-team-season.component';
import { AddPlayerIntoSeasonComponent } from './player-admin/add-player-into-season/add-player-into-season.component';
import { LoginComponent } from './login/login.component';
import { LayoutComponent } from './ui/layout/layout.component';
import { LayoutAdminComponent } from './ui/layout-admin/layout-admin.component';
import { ScheduleAdminComponent } from './schedule-admin/schedule-admin.component';
import { EditMatchComponent } from './schedule-admin/edit-match/edit-match.component';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { MatchesAdminComponent } from './matches-admin/matches-admin.component';
import { PlayersInMatchComponent } from './matches-admin/players-in-match/players-in-match.component';
import { MatchHappenedDetailsComponent } from './matches-admin/match-happened-details/match-happened-details.component';
import { AddMatchDetailComponent } from './matches-admin/add-match-detail/add-match-detail.component';
import { TeamAnnonymousComponent } from './team-annonymous/team-annonymous.component';
import { ThreeStepForMatchDetailComponent } from './matches-admin/three-step-for-match-detail/three-step-for-match-detail.component';
import { ListUploadComponent } from './list-upload/list-upload.component';
import { FormUploadComponent } from './form-upload/form-upload.component';
import { TestAuthorityComponent } from './test-authority/test-authority.component';
import {httpInterceptorProviders} from './ApiService/auth-login/auth-interceptor';
import { UnauthorityComponent } from './unauthority/unauthority.component';
import { CardTeamComponent } from './team-annonymous/card-team/card-team.component';
import { ScheduleAnnonymousComponent } from './schedule-annonymous/schedule-annonymous.component';
import { DateFormatPipe } from './ApiService/date-format.pipe';
import { ShowMatchInfoAnnonymousComponent } from './show-match-info-annonymous/show-match-info-annonymous.component';
import { TableResultComponent } from './table-result/table-result.component';
import { AddLocationComponent } from './teams/add-location/add-location.component';
import { ShowDetailsForTeamComponent } from './team-annonymous/show-details-for-team/show-details-for-team.component';
import { TableDetailsComponent } from './team-annonymous/table-details/table-details.component';
import { StatisticForMatchComponent } from './show-match-info-annonymous/statistic-for-match/statistic-for-match.component';
import { ShowPlayerStatisticComponent } from './team-annonymous/show-player-statistic/show-player-statistic.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HeaderAdministratorComponent,
    TeamsComponent,
    TeamComponent,
    SeasonComponent,
    TeamsInfoComponent,
    NotFoundComponent,
    PlayerAdminComponent,
    PlayersInfoComponent,
    PlayerComponent,
    PlayerTeamSeasonComponent,
    AddPlayerIntoSeasonComponent,
    LoginComponent,
    LayoutComponent,
    LayoutAdminComponent,
    ScheduleAdminComponent,
    EditMatchComponent,
    MatchesAdminComponent,
    PlayersInMatchComponent,
    MatchHappenedDetailsComponent,
    AddMatchDetailComponent,
    TeamAnnonymousComponent,
    ThreeStepForMatchDetailComponent,
    ListUploadComponent,
    FormUploadComponent,
    TestAuthorityComponent,
    UnauthorityComponent,
    CardTeamComponent,
    ScheduleAnnonymousComponent,
    DateFormatPipe,
    ShowMatchInfoAnnonymousComponent,
    TableResultComponent,
    AddLocationComponent,
    ShowDetailsForTeamComponent,
    TableDetailsComponent,
    StatisticForMatchComponent,
    ShowPlayerStatisticComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    MaterialModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    OwlDateTimeModule,
    OwlNativeDateTimeModule
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent],
  entryComponents: [PlayerComponent, AddPlayerIntoSeasonComponent, EditMatchComponent,
    AddMatchDetailComponent, AddLocationComponent]
})
export class AppModule {}
