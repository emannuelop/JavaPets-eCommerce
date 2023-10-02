import { inject } from "@angular/core";
import { ActivatedRoute, ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot } from "@angular/router";
import { Estado } from "src/app/models/estado.model";
import { EstadoService } from "src/app/services/estado.service";

export const estadoResolver: ResolveFn<Estado> = 
    (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
        return inject(EstadoService).findById(route.paramMap.get('id')!);
    };
