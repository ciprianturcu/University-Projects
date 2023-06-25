import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InsertComponent } from './components/insert/insert.component';
import { MainComponent } from './components/main/main.component';
import { UpdateComponent } from './components/update/update.component';

const routes: Routes = [
  {path: 'insert', component:InsertComponent},
  {path: 'update/:id', component:UpdateComponent},
  {path: 'main', component:MainComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
