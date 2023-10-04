
export class Usuario {
  id!: number;
  login!: string;
  senha!: string;
  nome!: string; // Nome da pessoa física
  cpf!: string; // CPF da pessoa física
  email!: string;
  sexo!: number;
  endereco!: Endereco;
  telefones!: Telefone[]; // Agora é uma lista de telefones
}

export class Endereco {
  logradouro!: string;
  bairro!: string;
  numero!: string;
  complemento!: string;
  cep!: string;
  idMunicipio!: number;
}

export class Telefone {
  codigoArea!: string;
  numero!: string;
}
