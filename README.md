# 🎲 Ponderada 01 — M10
 
> **Autor:** Davi Nascimento de Jesus  
> **Tecnologias:** Kotlin · Jetpack Compose · Android Studio
 
A atividade consistia em identificar um bug em um projeto Android base e evoluir o app para suportar múltiplos tipos de dados.
 
---
 
## Sumário
 
1. [Preparação do ambiente de desenvolvimento](#seção-1--preparação-do-ambiente-de-desenvolvimento)
2. [Diagnóstico e correção do bug](#seção-2--diagnóstico-e-correção-do-bug)
3. [Implementação dos novos tipos de dados](#seção-3--implementação-dos-novos-tipos-de-dados)
4. [Diferencial visual com tema Pokémon](#seção-4--diferencial-visual-com-tema-pokémon)
---
 
## Seção 1 — Preparação do ambiente de desenvolvimento
 
O Android Studio foi instalado via Snap. Logo de início apareceram problemas de compatibilidade com o **Android Gradle Plugin**, que precisou ser ajustado para funcionar com a versão do projeto.
 
Na hora de testar, o emulador travou a máquina, então a alternativa foi usar um **tablet físico do laboratório**, o que resolveu e permitiu seguir com o desenvolvimento normalmente.
 
---
 
## Seção 2 — Diagnóstico e correção do bug
 
O problema estava na geração do número aleatório. O código original usava:
 
```kotlin
Random.nextInt(6)
```
 
Essa chamada produz valores de **0 a 5** — intervalo errado para um dado de seis faces.
 
> ⚠️ Este é um erro clássico de *off-by-one*: a quantidade de valores possíveis estava certa, mas o intervalo estava deslocado por uma unidade.
 
A correção foi especificar explicitamente os dois limites:
 
```kotlin
"D6" -> Random.nextInt(1, 7)
```
 
---
 
## Seção 3 — Implementação dos novos tipos de dados
 
Com o D6 funcionando, a expansão seguiu de forma natural. Os tipos de dados foram organizados em uma lista:
 
```kotlin
val dados = listOf("D6", "D10", "D20", "D100")
```
 
Os botões de seleção são gerados dinamicamente com `forEach`, evitando repetição de código. Um estado `dadoSelecionado` controla qual opção está ativa, e um bloco `when` determina o intervalo correto na hora do sorteio:
 
```kotlin
val valorSorteado = when (dadoSelecionado) {
    "D6"   -> Random.nextInt(1, 7)
    "D10"  -> Random.nextInt(1, 11)
    "D20"  -> Random.nextInt(1, 21)
    "D100" -> Random.nextInt(1, 101)
    else   -> 0
}
```
 
| Dado | Intervalo válido |
|------|-----------------|
| D6   | 1 a 6           |
| D10  | 1 a 10          |
| D20  | 1 a 20          |
| D100 | 1 a 100         |
 
---
 
## Seção 4 — Diferencial visual com tema Pokémon
 
A parte extra foi exibir a imagem da face correspondente ao resultado quando o **D6** é lançado. Para deixar o visual mais temático, cada face usa uma imagem de um Pokémon diferente — conectando o app a um universo familiar e tornando a experiência mais divertida.
 
As imagens estavam originalmente em SVG dentro de uma subpasta, formato que o Android não reconhece como recurso diretamente. Por isso foram convertidas para um formato compatível e movidas para `res/drawable`.
 
Um estado nullable controla qual imagem aparece na tela:
 
```kotlin
var imagemDoDado by remember { mutableStateOf<Int?>(null) }
```
 
> 💡 Quando o dado selecionado não é o D6, o valor permanece `null` e nenhuma imagem é renderizada — evitando qualquer exibição indevida para os outros tipos de dado.
