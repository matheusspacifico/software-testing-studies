
### Entradas Inválidas

| ID | Entrada                          | Saida Esperada           | Contadores (Statistics) |
|----|----------------------------------|--------------------------|-------------------------|
| T1 | Data é null                      | NullPointerException     | Nenhum                  |
| T2 | Data com subarray de 5 elementos | IllegalArgumentException | Nenhum                  |
| T3 | Data com subarray de 7 elementos | IllegalArgumentException | Nenhum                  |

### Entradas Válidas

| ID | Entrada                                     | Saida Esperada    | Contadores (Statistics) |
|----|---------------------------------------------|-------------------|-------------------------|
| T4 | Data é vazio                                | Objeto Statistics | (0, 0, 0, 0)            |
| T5 | Data com 1 subarray que não forma triângulo | Objeto Statistics | (1, 0, 0, 0)            |
| T6 | Data com 1 subarray que forma equilátero    | Objeto Statistics | (0, 1, 0, 0)            |
| T7 | Data com 1 subarray que forma isósceles     | Objeto Statistics | (0, 0, 1, 0)            |
| T8 | Data com 1 subarray que forma escaleno      | Objeto Statistics | (0, 0, 0, 1)            |
| T9 | Data com 4 subarray um de cada              | Objeto Statistics | (1, 1, 1, 1)            |