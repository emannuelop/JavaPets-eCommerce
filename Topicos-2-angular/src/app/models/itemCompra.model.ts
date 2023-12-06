import { Produto } from "./produto.model";

export class ItemCompra {
    id!: number;
    quantidade!: number;
    precoUnitario!: number;
    produto!: Produto;
}