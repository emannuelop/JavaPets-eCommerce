import { Categoria } from "./categoria.model";
import { Fornecedor } from "./fornecedor.model";
import { Marca } from "./marca.model";

export class Produto {
    id!: number;
    nome!: string;
    nomeImagem!: string;
    descricao!: string;
    preco!: number;
    estoque!: number;
    marca!: Marca;
    fornecedor!: Fornecedor;
    categoria!: Categoria;
}
