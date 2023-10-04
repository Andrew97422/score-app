import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { SessionService } from './session.service';
import { DataService } from './data.service';
import { AuthorizationSource } from '../models/authorization-source';

declare var VK: any;
@Injectable({
    providedIn: 'root'
})
export class VKService {
    constructor(
        private sessionService: SessionService,
        private dataService: DataService,
        private router: Router) {
            this.init();
    }

    init(): void {
        (window as any).vkAsyncInit = function () {
          VK.init({
            apiId: '51715554',
          });
        };
        
        setTimeout(function() {
            var el = document.createElement('script');
            el.type = 'text/javascript';
            el.src = 'https://vk.com/js/api/openapi.js?169';
            el.async = true;
            document.getElementById('vk_api_transport')?.appendChild(el);
        }, 0);
    }

    getProfile(): void {
        const profileReq = {
            user_ids: this.sessionService.getSessionID(),
            fields: 'photo_100,city,birthday,phone',
            v: '5.81'
        };

        VK.Api.call('users.get', profileReq, r => {
            if (r.response) {
                this.dataService.storeUserProfile({
                        firstName: r.response[0].first_name,
                        lastName: r.response[0].last_name,
                        birthday: r.response[0].birthday,
                        surName: r.response[0].surName,
                        phone: r.response[0].phone,
                        city: r.response[0].city ? r.response[0].city.title : 'предпочитает не указывать',
                        photoUrl: r.response[0].photo_100
                    }
                );
            }
        });
    }

    login(): void {
        VK.Auth.login((response) => {
            if (response.session) {
                this.sessionService.createSession(response.session.user.id, true, '', AuthorizationSource.VK);
                this.getProfile();

                this.router.navigate(['']);
              /* Пользователь успешно авторизовался */
              if (response.settings) {
                /* Выбранные настройки доступа пользователя, если они были запрошены */
              }
            } else {
              /* Пользователь нажал кнопку Отмена в окне авторизации */
            }
        });
    }

    logout(): void {
        VK.Auth.logout();
        this.sessionService.destroySession();
        this.router.navigate(['login']);
      }
}