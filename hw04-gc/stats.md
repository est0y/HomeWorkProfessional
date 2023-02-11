| HEAP SIZE, Mb | TIME BEFORE OPTIMIZATION, ms | TIME AFTER OPTIMIZATION, ms |
|---------------|------------------------------|-----------------------------|
| 64            | OutOfMemory                  | 1212                        |
| 128           | OutOfMemory                  | 1264                        |
| 256           | OutOfMemory                  | 1186                        |
| 512           | 11985                        | 1118                        |
| 1024          | 10350                        | 1198                        |
| 2048          | 9456                         | 1205                        |
| 4096          | 9383                         | 1191                        |
| 8192          | 13190                        | 1146                        |


|                     | optimal heap size,Mb |
|---------------------|----------------------|
| BEFORE OPTIMIZATION | **4096**             |
| AFTER OPTIMIZATION  | **512**              |