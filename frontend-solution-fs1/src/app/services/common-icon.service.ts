import { inject, Injectable } from '@angular/core';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';

@Injectable({
  providedIn: 'root'
})
export class CommonIconService {

  iconRegistry = inject(MatIconRegistry);
  domSanitizer = inject(DomSanitizer);

  constructor() {}

  public registerIcons(): void {
    this.iconRegistry.addSvgIcon(
      'github',
      this.domSanitizer.bypassSecurityTrustResourceUrl('../../assets/icons/github-circle-white-transparent.svg')
    );
  }
  
}
