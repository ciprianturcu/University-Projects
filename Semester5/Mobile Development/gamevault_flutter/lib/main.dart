import 'package:flutter/material.dart';
import 'package:gamevault_flutter/overview_screen.dart';

void main() {
  runApp(GameVault());
}

class GameVault extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.indigo,
      ),
      initialRoute: '/Overview',
      routes: <String, WidgetBuilder> {
        '/Overview' :(context) => OverviewScreen(),
      },
    );
  }
}