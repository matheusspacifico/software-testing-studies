
### Entradas Nulas e Vazias

| ID | Entrada      | Saida Esperada           | Produto Atualizado? (Repository Chamado) |
|----|--------------|--------------------------|------------------------------------------|
| T1 | Produto null | IllegalArgumentException | Não                                      |

### Entradas Inválidas (Pré-Condições)

| ID | Entrada                               | Saida Esperada           | Produto Atualizado? (Repository Chamado) |
|----|---------------------------------------|--------------------------|------------------------------------------|
| T2 | Produto que não existe no repositório | IllegalArgumentException | Não                                      |

### Entradas Válidas // LEMBRANDO QUE NULL É VÁLIDO, E SIGNIFICA QUE O CAMPO NULL SERÁ SKIPADO

| ID  | Entrada                                      | Saida Esperada                                         | Produto Atualizado? (Repository Chamado)          |
|-----|----------------------------------------------|--------------------------------------------------------|---------------------------------------------------|
| T3  | Produto com todos os campos válidos não null | Lista de entradas inválidas vazia                      | Sim, todos os campos                              |
| -   | -                                            | -                                                      | -                                                 |
| T4  | Produto com nome null                        | Lista de entradas inválidas vazia                      | Sim, todos os campos menos nome                   |
| T5  | Produto com nome vazio                       | Lista de entradas inválidas com nome                   | Sim, todos os campos menos nome                   |
| T6  | Produto com preço null                       | Lista de entradas inválidas vazia                      | Sim, todos os campos menos preço                  |
| T7  | Produto com preço <= 0                       | Lista de entradas inválidas com preço                  | Sim, todos os campos menos preço                  |
| T8  | Produto com categoria null                   | Lista de entradas inválidas vazia                      | Sim, todos os campos menos categoria              |
| T9  | Produto com quantidade null                  | Lista de entradas inválidas vazia                      | Sim, todos os campos menos quantidade             |
| T10 | Produto com quantidade <= 0                  | Lista de entradas inválidas com quantidade             | Sim, todos os campos menos quantidade             |
| T11 | Produto com descrição null                   | Lista de entradas inválidas vazia                      | Sim, todos os campos menos descrição              |
| T12 | Produto com descrição vazia                  | Lista de entradas inválidas com descrição              | Sim, todos os campos menos descrição              |
| -   | -                                            | -                                                      | -                                                 |
| T13 | Produto com todos os campos null             | Lista de entradas inválidas vazia                      | Não                                               |
| T14 | Produto com todos os campos iguais           | Lista de entradas inválidas vazia                      | Não                                               |
| T15 | Produto com todos os campos inválidos        | Lista de entradas inválidas com todos os campos        | Não                                               |
| -   | -                                            | -                                                      | -                                                 |
| T16 | Produto com nome e preço inválidos           | Lista de entradas inválidas com nome e preço           | Sim, todos os campos menos nome e preço           |
| T17 | Produto com categoria e quantidade inválidos | Lista de entradas inválidas com categoria e quantidade | Sim, todos os campos menos categoria e quantidade |
| -   | -                                            | -                                                      | -                                                 |
| T18 | Produto com tudo null menos o preço          | Lista de entradas inválidas vazia                      | Sim, apenas o preço                               |
| T19 | Produto com tudo null menos a quantidade     | Lista de entradas inválidas vazia                      | Sim, apenas a quantidade                          |
| T20 | Produto com tudo null menos descrição        | Lista de entradas inválidas vazia                      | Sim, apenas a descrição                           |