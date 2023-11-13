import 'package:flutter/material.dart';
import 'package:gamevault_flutter/add_screen.dart';
import 'package:gamevault_flutter/custom_progress_bar.dart';
import 'package:gamevault_flutter/db_helper.dart';
import 'package:gamevault_flutter/detail_screen.dart';
import 'package:gamevault_flutter/game.dart';

class OverviewScreen extends StatefulWidget {
  @override
  State<OverviewScreen> createState() => _OverviewScreenState();
}

class _OverviewScreenState extends State<OverviewScreen> {
  late DatabaseHelper databaseHelper;

  late Future<List<Game>?> _gameList;

  @override
  void initState() {
    super.initState();
    databaseHelper = DatabaseHelper();
    databaseHelper.databaseInit().whenComplete(() async {});
    _gameList = databaseHelper.retrieveGames();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFF212223),
      appBar: AppBar(
        centerTitle: true,
        title: const Text(
            "Game Vault",
            style: TextStyle(fontWeight: FontWeight.w700, fontSize: 34.0),
          ),
        backgroundColor: const Color(0xFF212223),
      ),
      body: FutureBuilder<List<Game>?>(
        future: _gameList,
        builder: (context, AsyncSnapshot<List<Game>?> snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const CircularProgressIndicator();
          } else if (snapshot.hasError) {
            return Center(
              child: Text(snapshot.error.toString()),
            );
          } else if (snapshot.data == null) {
            return const Text('Game not found');
          } else {
            return ListView.builder(
              itemCount: snapshot.data?.length,
              itemBuilder: (context, index) => Card(
                margin: const EdgeInsets.all(15),
                child: GestureDetector(
                  onTap: () async {
                    await Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) => GameDetailScreen(
                                gameIndex: snapshot.data![index].id ?? -1)));
                    setState(() {
                      _gameList = databaseHelper.retrieveGames();
                    });
                  },
                  child: ListTile(
                    tileColor: const Color(0xFF35363A),
                    title: Center(
                      child: Padding(
                        padding: const EdgeInsets.symmetric(vertical: 5),
                        child: Text(
                          snapshot.data![index].title,
                          style: const TextStyle(
                              fontWeight: FontWeight.w500, fontSize: 25.0, color: Color(0xFFE0E0E2)),
                        ),
                      ),
                    ),
                    subtitle: Column(
                      children: [
                        SizedBox(
                          width: double.infinity,
                          height: 50,
                          child: Row(
                            children: [
                              Text(snapshot.data![index].genre, style: const TextStyle(color: Color(0xFFE0E0E2)),),
                              const Spacer(),
                              Text("${snapshot.data![index].hoursPlayed} hours", style: const TextStyle(color: Color(0xFFE0E0E2)),),
                              const Spacer(),
                              Text("${snapshot.data![index].progress}%", style: const TextStyle(color: Color(0xFFE0E0E2)),),
                            ],
                          ),
                        ),
                        SizedBox(
                            width: double.infinity,
                            height: 10,
                            child: CustomProgressBar(
                              percent: snapshot.data![index].progress,
                              backgroundColor: const Color(0xFF212121),
                              foregroundGradient: const LinearGradient(colors: [
                                Color(0xFFDF921F),
                                Color(0xFFFFD232)
                              ]),
                              isShownText: false,
                            )),
                      ],
                    ),
                  ),
                ),
              ),
            );
          }
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          await Navigator.push(
              context, MaterialPageRoute(builder: (context) => AddScreen()));
          setState(() {
            _gameList = databaseHelper.retrieveGames();
          });
        },
        child: const CircleAvatar(
          radius: 30,
          backgroundColor: Color(0xFF47494F),
          child: Icon(
            Icons.add_rounded,
            color: Color(0xFFFFD232),
            size: 40,
          ),
        )
      ),
    );
  }
}
