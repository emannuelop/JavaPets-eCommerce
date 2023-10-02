import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot } from "@angular/router";
import { Produto } from "src/app/models/produto.model";
import { ProdutoService } from "src/app/services/produto.service";

export const produtoResolver: ResolveFn<Produto> = 
    (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
        return inject(ProdutoService).findById(route.paramMap.get('id')!);
    };
