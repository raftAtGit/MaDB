import { PipeTransform, Pipe } from '@angular/core';

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {

  constructor() { }

  transform(projects: any[], search: string) {
    const searchInput = search.toLowerCase();
    return projects.filter(project => project.projectId.toLowerCase().includes(searchInput)
      || project.name.toLowerCase().includes(searchInput));
  }

}
