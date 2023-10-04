import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot } from "@angular/router";
import { cupomDesconto } from "src/app/models/cupomDesconto.model";
import { cupomDescontoService } from "src/app/services/cupomDesconto.service";

export const cupomDescontoResolver: ResolveFn<cupomDesconto> = 
    (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
        return inject(cupomDescontoService).findById(route.paramMap.get('id')!);
    };
