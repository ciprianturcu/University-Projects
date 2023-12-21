import 'package:flutter/material.dart';
import 'package:gamevault_flutter/add_screen.dart';
import 'package:gamevault_flutter/db_helper.dart';
import 'package:gamevault_flutter/detail_screen.dart';
import 'package:gamevault_flutter/game_viewmodel.dart';
import 'package:gamevault_flutter/overview_screen.dart';
import 'package:gamevault_flutter/update_screen.dart';
import 'package:provider/provider.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();

  DatabaseHelper gameDatabase = DatabaseHelper();
  await gameDatabase.database;
  runApp(ChangeNotifierProvider(create: (context) => GameViewModel(), child: const GameVault()));
}

class GameVault extends StatelessWidget {
  const GameVault({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.indigo,
      ),
      initialRoute: '/overview',
      routes: <String, WidgetBuilder>{
        '/overview': (context) => const OverviewScreen(),
        '/gameDetail': (context) => const GameDetailScreen(),
        '/addGame' :(context) => const AddScreen(),
        '/updateGame' :(context) => UpdateScreen(),
      },
    );
  }
}
 