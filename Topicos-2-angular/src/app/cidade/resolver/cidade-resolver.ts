import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot } from "@angular/router";
import { Cidade } from "src/app/models/cidade.model";
import { CidadeService } from "src/app/services/cidade.service";

export const cidadeResolver: ResolveFn<Cidade> = 
    (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
        return inject(CidadeService).findById(route.paramMap.get('id')!);
    };
