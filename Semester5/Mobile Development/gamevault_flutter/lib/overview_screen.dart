import 'package:flutter/material.dart';
import 'package:gamevault_flutter/custom_progress_bar.dart';
import 'package:gamevault_flutter/game.dart';
import 'package:gamevault_flutter/game_viewmodel.dart';
import 'package:logger/logger.dart';
import 'package:provider/provider.dart';

final Logger _log = Logger();

class OverviewScreen extends StatefulWidget {
  const OverviewScreen({Key? key}) : super(key: key);

  @override
  State<OverviewScreen> createState() => _OverviewScreenState();
}

class _OverviewScreenState extends State<OverviewScreen> {
  @override
  Widget build(BuildContext context) {
    _log.d('entered render of overview screen');
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
      body: const GameListView(),
      floatingActionButton: const AddButton(),
    );
  }
}

class GameListView extends StatelessWidget {
  const GameListView({super.key});

  @override
  Widget build(BuildContext context) {
    final gameViewModel = Provider.of<GameViewModel>(context);
    final games = gameViewModel.games;
    _log.d('entered render of list in overview screen');
    return ListView.builder(
      itemCount: games.length,
      itemBuilder: (context, index) {
        final game = games[index];
        return GameItem(
          game: game,
          onTap: () {
            Navigator.pushNamed(context, '/gameDetail',
                arguments: {'gameId': game.id});
          },
        );
      },
    );
  }
}

class GameItem extends StatelessWidget {
  final Game game;
  final VoidCallback onTap;

  const GameItem({super.key, required this.game, required this.onTap});

  @override
  Widget build(BuildContext context) {
    _log.d('entered render of individual item from list in overview screen');
    return Card(
      margin: const EdgeInsets.all(15),
      child: ListTile(
        onTap: onTap,
        tileColor: const Color(0xFF35363A),
        title: Center(
          child: Padding(
            padding: const EdgeInsets.symmetric(vertical: 5),
            child: Text(
              game.title,
              style: const TextStyle(
                  fontWeight: FontWeight.w500,
                  fontSize: 25.0,
                  color: Color(0xFFE0E0E2)),
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
                  Text(
                    game.genre,
                    style: const TextStyle(color: Color(0xFFE0E0E2)),
                  ),
                  const Spacer(),
                  Text(
                    "${game.hoursPlayed} hours",
                    style: const TextStyle(color: Color(0xFFE0E0E2)),
                  ),
                  const Spacer(),
                  Text(
                    "${game.progress}%",
                    style: const TextStyle(color: Color(0xFFE0E0E2)),
                  ),
                ],
              ),
            ),
            SizedBox(
                width: double.infinity,
                height: 10,
                child: CustomProgressBar(
                  percent: game.progress,
                  backgroundColor: const Color(0xFF212121),
                  foregroundGradient: const LinearGradient(
                      colors: [Color(0xFFDF921F), Color(0xFFFFD232)]),
                  isShownText: false,
                )),
          ],
        ),
      ),
    );
  }
}

class AddButton extends StatelessWidget {
  const AddButton({super.key});

  @override
  Widget build(BuildContext context) {
    return FloatingActionButton(
        onPressed: () {
          Navigator.pushNamed(context, '/addGame');
        },
        child: const CircleAvatar(
          radius: 30,
          backgroundColor: Color(0xFF47494F),
          child: Icon(
            Icons.add_rounded,
            color: Color(0xFFFFD232),
            size: 40,
          ),
        ));
  }
}
