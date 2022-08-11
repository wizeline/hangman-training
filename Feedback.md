### Feedback

Muy buen trabajo! Comprendiste y aplicaste los conceptos explicados en el bootcamp. Tengo solo unos pocos comentarios para tu código:

- Ten cuidado con algunas variables del ViewModel en las que expusiste MutableLiveData en lugar de LiveData. Veo que lo implementaste correctamente en la mayoría pero no está correcto en HomeViewModel y ScoreViewModel.
- Evita colocar directamente los Schedulers en subscribeOn/observeOn y Dispatchers en withContext. Siempre se recomienda inyectarlos a través del constructor, también para facilitar la parte de testing.
- En coroutines, es importante que marques las funciones con suspend. Esta es la forma que tiene Kotlin para saber que la función se puede comenzar, pausar o reanudar en la ejecución de la coroutine, que es la base de la concurrencia. En general se marcan con suspend todas las funciones de tu capa de datos en las que uses coroutines.
- Por último, no encontré tests para Repository y ViewModel. Recuerda que es importante agregar tests para mantener la calidad de tu código y protegerlo de bugs.

