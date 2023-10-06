import { Cidade } from "./cidade.model";

export class Usuario {
  id!: number;
  login!: string;
  senha!: string;
  pessoaFisicaDto!:PessoaFisicaDto
  endereco!: Endereco;
  telefones!: Telefone[]; // Agora é uma lista de telefones
}
export class PessoaFisicaDto {
  nome!: string;
  cpf!: string;
  email!: string;
  sexo!: number;
}

export class Endereco {
  logradouro!: string;
  bairro!: string;
  numero!: string;
  complemento!: string;
  cep!: string;
  cidade!: Cidade;
}

export class Telefone {
  codigoDeArea!: string;
  numero!: string;
}
