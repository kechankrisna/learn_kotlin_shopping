@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.shopping

///
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopping.ui.theme.ShoppingTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Integer.parseInt


class MainActivity : ComponentActivity() {

    override fun onResume() {
        Log.i("MainActivity", "onResume");
        super.onResume()
    }

    override fun onDestroy() {
        Log.i("MainActivity", "onDestroy");
        super.onDestroy()

    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("MainActivity", "onCreate");
        super.onCreate(savedInstanceState)


        setContent {
            ShoppingTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ScaffoldDefaultDrawer(drawerState = DrawerState(DrawerValue.Open))
                    HomeScreen()
                }
            }
        }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var context: android.content.Context = LocalContext.current;
    var counter = remember { mutableStateOf(0) }
    // TODO: state management by value
    var name by remember { mutableStateOf("") }
    // TODO: state for show or hide dialog
    var openAlertDialog by remember { mutableStateOf(false) }
    var openDrawer by remember { mutableStateOf(false) }

    val increase = fun(): Unit {
        counter.value++;
        return;
    }
    val onValueChanged = fun(v: String): Unit {
        name = v;
        return;
    }
    val toggleDialog = fun(v: Boolean?): Unit {
        openAlertDialog = v ?: false;
        return;
    }

    when {
        openAlertDialog -> {
            AlertDialogExample(
                onDismissRequest = { openAlertDialog = false },
                onConfirmation = {
                    openAlertDialog = false
                    counter.value = it ?: 0
                    println("Confirmation registered") // Add logic here to handle confirmation.
                },
                dialogTitle = "Alert dialog example",
//                dialogText = "This is an example of an alert dialog with buttons.",
                icon = Icons.Default.Info,
                initialValue = counter.value,
            )
        }


    }

    Scaffold(

        topBar = {
            TopAppBar(

//                navigationIcon = {
//                    IconButton(onClick = {
//
//                    }, content = {
//                        Icon(Icons.Default.Menu, contentDescription = "menu")
//                    })
//                },
                title = {
                    Text("home screen")
                },
                actions = {
                    Row {
                        IconButton(onClick = {
                            ///
                        }, content = {
                            Icon(Icons.Default.AccountBox, contentDescription = "account")
                        })
                        IconButton(onClick = {
                            ///
                        }, content = {
                            Icon(Icons.Default.Menu, contentDescription = "menu")
                        })
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Text("bottom app bar")
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = increase) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            content = {
                OutlinedTextField(
                    value = name,
                    onValueChange = onValueChanged,
                    label = {
                        Text("name is")
                    }
                )
                if (!name.isEmpty()) Text("current name ${name}")
                Text("counter ${counter.value}")
                OutlinedButton(onClick = {
                    Toast.makeText(context, "hello world", Toast.LENGTH_SHORT).show()
                }, content = {
                    Text("show toast bar")
                })

                OutlinedButton(onClick = {
                    toggleDialog(true)
                }, content = {
                    Text("show dialog")
                })

                ServiceComponent()

                HookingComponent()

                WorkingList()


            },
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShoppingTheme {
        HomeScreen()
    }
}


@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: (v: Int?) -> Unit,
    dialogTitle: String,
    dialogText: String? = null,
    icon: ImageVector,
    initialValue: Int? = null
) {
    // TODO: context for show toast or dialog
    var context: android.content.Context = LocalContext.current;
    // TODO: state management by reference
    var newValue = remember { mutableStateOf(initialValue ?: 0) }

    val onValueChanged = fun(v: String): Unit {
        // TODO: tenary operator
        newValue.value = if (!v.isEmpty()) parseInt(v) else 0;
        return;
    }

    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            if (dialogText != null) Text(text = dialogText)
            if (dialogText == null) Column {
                Text("Enter the new value")
                OutlinedTextField(
                    value = newValue.value.toString(),
                    onValueChange = onValueChanged,
                    label = {
                        Text("value is")
                    },

                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation(newValue.value)
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
fun ScaffoldDefaultDrawer(drawerState: DrawerState): Unit {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text("Hello World")
                }
            }
        },
        content = {
            ModalDrawerSheet {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text("Hello World")
                }
            }

        },
    )
}

enum class DataState { LOADING, DONE }

@Composable
fun WorkingList(): Unit {
    var context = LocalContext.current;
    var datas = remember { mutableStateOf(mutableListOf<String>()) }
    var dataState = remember { mutableStateOf(DataState.DONE) };

    suspend fun getDataFromSharedPreference(): Unit {
        datas.value = mutableListOf();
        dataState.value = DataState.LOADING;

        delay(1000)
        var sharedPreference = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE);
        var setValues = sharedPreference.getStringSet("values", setOf<String>());
        var _datas = setValues?.toMutableList<String>() ?: mutableListOf<String>()
//        println(_datas)
        datas.value = _datas;
        dataState.value = DataState.DONE;
        return;
    }

    fun saveToSharedPreference(): Unit {
        var sharedPreference = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE);
        var editor = sharedPreference.edit();
        editor.putStringSet("values", datas.value.toSet<String>())
        editor.commit();
        return;
    }

    fun addData(): Unit {
        var copies = datas.value.map { it }.toMutableList();
        copies.add(copies.size, "hello ${copies.size + 1}");
        datas.value = copies;
        Toast.makeText(context, "data added success", Toast.LENGTH_SHORT).show();
        //
        saveToSharedPreference();
    }

    fun removeData(index: Int): Unit {
        var copies = datas.value.map { it }.toMutableList();
        copies.removeAt(index);
        datas.value = copies;

        Toast.makeText(context, "data removed success", Toast.LENGTH_SHORT).show();
        //
        saveToSharedPreference();
    }

    var coroutine = rememberCoroutineScope();
    LaunchedEffect(coroutine) {
        Log.d("state", "LaunchedEffect")
        getDataFromSharedPreference();
    }
    DisposableEffect(Unit) {

        onDispose {
            Log.d("state", "onDispose")
        }

    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = {
            ElevatedButton(onClick = {
                coroutine.launch {
                    getDataFromSharedPreference()
                }

            }, content = {
                Text("load data")
            })
            ElevatedButton(onClick = {
                addData()
            }, content = {
                Text("add value to datas")
            })

            Box(
                modifier = Modifier.heightIn(min = 100.0.dp, max = 200.0.dp)
            ) {

                if (!datas.value.isEmpty() && dataState.value == DataState.DONE) LazyColumn {
                    items(datas.value.size) {
                        ListItem(
                            modifier = Modifier.padding(4.0.dp),

                            headlineText = {
                                Text(datas.value[it].toString())
                            }, trailingContent = {
                                IconButton(
                                    onClick = { removeData(it) },
                                    content = { Icon(Icons.Filled.Delete, "remove element") }
                                )
                            })
                        Divider(modifier = Modifier.padding(0.0.dp))
                    }
                } else if (dataState.value == DataState.LOADING) LinearProgressIndicator() else Text("no data")
            }

        },
    )
}

/**
 * Extension functions
 * https://kotlinlang.org/docs/extensions.html#extension-functions
 */
fun MutableList<String>.extra(v: String): MutableList<String> {
    var copies = this.toMutableList();
    copies.add(copies.size, v)
    return copies;
}


@Composable
fun ServiceComponent(): Unit {

    var context = LocalContext.current;
    var serviceClassName = MyService::class.qualifiedName!!;
    var serviceState = remember { mutableStateOf(MyServiceObject.isServiceRunning(context, serviceClassName)) }


    LaunchedEffect(Unit) {
        Log.d("service", "ServiceComponent")

    }

    SideEffect {
        Log.d("service", "SideEffect")
    }

    DisposableEffect(Unit) {

        onDispose {

        }
    }


    OutlinedButton(onClick = {
        if (!serviceState.value) {
            context.startService(Intent(context, MyService::class.java))
            serviceState.value = true;

            var isRunning = MyServiceObject.isServiceRunning(context, serviceClassName);
            Log.d("service", "isRunning $isRunning")
        } else {
            context.stopService(Intent(context, MyService::class.java))
            serviceState.value = false
        }
    }, content = {
        Text(if (!serviceState.value) "start service" else "stop service")
    })
}

@Composable
fun HookingComponent(): Unit {
    var context = LocalContext.current;

    var (x, setValue) = remember {
        mutableStateOf(0)
    }

    fun test() {
//        var json = """
//        {
//        "packageName": 1,
//        "processName": "Hello",
//        "classLoader": 11,
//        "appInfo":1000.0,
//        "isFirstApplication": 10
//    }
//    """.trimIndent();
//        var gson = Gson();
//        var llparam = gson.fromJson<XC_LoadPackage.LoadPackageParam>(json, XC_LoadPackage.LoadPackageParam::class.java)
//        Log.i("service", llparam.toString())
//        EdxposedTester();

    }

    ElevatedButton(onClick = { test() }, content = { Text("test") })
}
