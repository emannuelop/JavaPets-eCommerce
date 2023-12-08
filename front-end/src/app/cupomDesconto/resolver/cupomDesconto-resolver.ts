import { inject } from "@angular/core";
import { ActivatedRoute, ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot } from "@angular/router";
import { CupomDesconto } from "src/app/models/cupomDesconto.model";
import { CupomDescontoService } from "src/app/services/cupomDesconto.service";

export const cupomDescontoResolver: ResolveFn<CupomDesconto> = 
    (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
        return inject(CupomDescontoService).findById(route.paramMap.get('id')!);
    };
