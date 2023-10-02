import { inject } from "@angular/core";
import { ActivatedRoute, ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot } from "@angular/router";
import { Marca } from "src/app/models/marca.model";
import { MarcaService } from "src/app/services/marca.service";

export const marcaResolver: ResolveFn<Marca> = 
    (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
        return inject(MarcaService).findById(route.paramMap.get('id')!);
    };
