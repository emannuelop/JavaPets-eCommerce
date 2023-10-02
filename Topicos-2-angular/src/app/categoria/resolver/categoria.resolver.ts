import { inject } from "@angular/core";
import { ActivatedRoute, ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot } from "@angular/router";
import { Categoria } from "src/app/models/categoria.model";
import { CategoriaService } from "src/app/services/categoria.service";

export const categoriaResolver: ResolveFn<Categoria> = 
    (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
        return inject(CategoriaService).findById(route.paramMap.get('id')!);
    };
