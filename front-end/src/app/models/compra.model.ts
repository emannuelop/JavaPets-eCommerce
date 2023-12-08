import { ItemCompra } from "./itemCompra.model";
import { Pagamento } from "./pagamento.model";
import { Endereco, Usuario } from "./usuario.model";

export class Compra {
    id!: number;
    dataCompra!: Date;
    totalCompra!: number;
    ifConcluida!: boolean;
    endereco!: Endereco;
    pagamento!: Pagamento;
    usuario!: Usuario;
    itemCompra!: ItemCompra[];
}