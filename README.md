# Java Banking 
Projeto realizado para estudos e aprendizado de Java no bootcamp DIO Java para iniciantes.

# Descrição
Este é um sistema bancário simples, desenvolvido em Java, com foco em conceitos de programação orientada a objetos, encapsulamento, herança e polimorfismo. O projeto simula operações bancárias como criação de contas, depósitos, saques, investimentos e transferências.

# Funcionalidades
- Criação de contas bancárias com múltiplas chaves PIX
- Depósitos e saques em contas
- Transferências entre contas via PIX
- Criação e gerenciamento de investimentos
- Carteiras de investimento vinculadas a contas
- Histórico de transações financeiras
- Relatórios de contas e investimentos

# Estrutura do Projeto
src/
  main/
    java/
      br/com/alx/
        Main.java
        exception/
        model/
        repository/
    resources/
  test/
    java/
    resources/
build.gradle.kts
settings.gradle.kts
# Principais Classes
- Main.java: Interface de linha de comando para interação com o sistema.
- Wallet.java: Abstração de carteira, utilizada por contas e investimentos.
- AccountWallet.java: Representa uma conta bancária.
- InvestmentWallet.java: Representa uma carteira de investimento.
- Money.java e MoneyAudit.java: Controlam saldo e histórico de transações.
- AccountRepository.java: Gerencia contas e operações bancárias. 
- InvestmentRepository.java: Gerencia investimentos e carteiras de investimento.
- exception/: Exceções customizadas para regras de negócio.
# Exceções Customizadas
- AccountNotFoundException: Conta não encontrada.
- PixInUseException: PIX já cadastrado.
- NoFundsEnoughException: Saldo insuficiente.
- AccountWithInvestmentException: Conta já possui investimento ativo.
- InvestmentNotFoundException: Investimento não encontrado.
- WalletNotFoundException: Carteira não encontrada.
# Fluxo de Uso
- Criar Conta: Informe as chaves PIX e valor inicial.
- Depositar/Sacar: Informe a chave PIX e valor.
- Transferir: Informe PIX de origem, destino e valor.
- Investir: Crie um investimento e vincule a uma conta.
- Consultar Histórico: Visualize transações por chave PIX.
# Como Executar
Certifique-se de ter o Java instalado (Java 17+ recomendado).
Execute o comando abaixo na raiz do projeto:
./gradlew run
No Windows, use:
gradlew.bat run

Observações Técnicas
O sistema utiliza listas em memória para simular persistência.
O saldo é representado por objetos Money em listas, e o histórico por MoneyAudit.
O projeto pode ser expandido para persistência real (JPA, JDBC, etc).
