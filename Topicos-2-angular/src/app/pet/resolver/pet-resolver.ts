import { inject } from "@angular/core";
import { ActivatedRoute, ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot } from "@angular/router";
import { Pet } from "src/app/models/pet.model";
import { PetService } from "src/app/services/pet.service";

export const petResolver: ResolveFn<Pet> = 
    (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
        return inject(PetService).findById(route.paramMap.get('id')!);
    };
