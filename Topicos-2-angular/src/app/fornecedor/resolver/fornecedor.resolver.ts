import { inject } from "@angular/core";
import { ActivatedRoute, ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot } from "@angular/router";
import { Fornecedor } from "src/app/models/fornecedor.model";
import { FornecedorService } from "src/app/services/fornecedor.service";

export const fornecedorResolver: ResolveFn<Fornecedor> = 
    (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
        return inject(FornecedorService).findById(route.paramMap.get('id')!);
    };
