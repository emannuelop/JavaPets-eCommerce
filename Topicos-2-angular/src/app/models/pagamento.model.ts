export class Pagamento {
    id!: number;
    valor!: number;
    confirmacaoPagamento!: boolean;
    dataConirmacaoPagamento!: Date;
}

export class BoletoBancario extends Pagamento {
    nome!: string;
    cpf!: string;
    dataGeracaoBoleto!: Date;
    dataVencimento!: Date;
}

export class Pix extends Pagamento {
    nome!: string;
    cpf!: string;
    dataExpiracaoTokenPix!: Date;
}

export class Cartao extends Pagamento {
    numeroCartao!: string;
    nomeImpressoCartao!: string;
    cpfTitular!: string;
    bandeiraCartao!: BandeiraCartao;
}

export class BandeiraCartao {
    id!: number;
    label!: string;
  }