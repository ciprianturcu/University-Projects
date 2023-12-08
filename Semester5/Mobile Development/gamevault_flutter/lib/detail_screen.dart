import 'package:flutter/material.dart';
import 'package:gamevault_flutter/game.dart';
import 'package:gamevault_flutter/game_viewmodel.dart';
import 'package:logger/logger.dart';
import 'package:provider/provider.dart';

class GameDetailScreen extends StatelessWidget {
  const GameDetailScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final Map<String, dynamic> args =
        ModalRoute.of(context)!.settings.arguments as Map<String, dynamic>;
    final int gameId = args['gameId'];
    return GameDetailScreenContent(gameId: gameId);
  }
}

class GameDetailScreenContent extends StatelessWidget {
  final int gameId;
  final Logger _log = Logger();

  GameDetailScreenContent({super.key, required this.gameId});

  @override
  Widget build(BuildContext context) {
    final gameViewModel = Provider.of<GameViewModel>(context);
    return FutureBuilder(
      future: gameViewModel.getGameById(gameId),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return const CircularProgressIndicator();
        } else if (snapshot.hasError || snapshot.data == null) {
          _log.e('error loading game : ${snapshot.error}');
          return const Text('Game not found');
        } else {
          Game game = snapshot.data!;
          return Scaffold(
            appBar: AppBar(
              centerTitle: true,
              title: const Text(
                '',
                style: TextStyle(fontWeight: FontWeight.w700, fontSize: 34.0),
              ),
              backgroundColor: const Color(0xFF212223),
              leading: IconButton(
                icon: const Icon(Icons.arrow_back),
                onPressed: () => Navigator.pop(context),
              ),
            ),
            backgroundColor: const Color(0xFF333437),
            body: Padding(
                padding: const EdgeInsets.all(8),
                child: Column(
                  children: [
                    const SizedBox(height: 20),
                    TitleAndGenreColumn(
                      gameTitle: game.title,
                      gameGenre: game.genre,
                    ),
                    const SizedBox(
                      height: 5,
                    ),
                    Expanded(
                      child: Card(
                          color: const Color(0xFF212223),
                          elevation: 4,
                          child: Padding(
                            padding: const EdgeInsets.all(8),
                            child: Column(
                              children: [
                                DescriptionRow(
                                    gameDescription: game.description),
                                ProgressAndRatingRow(
                                  gameProgress: game.progress.toString(),
                                  gameRating: game.rating.toString(),
                                ),
                                HoursPlayedRow(
                                    hoursPlayed: game.hoursPlayed.toString()),
                              ],
                            ),
                          )),
                    ),
                    ActionButtons(
                      onDeleteClick: () {
                        _showDeleteConfirmationDialog(context, gameViewModel);
                      },
                      onUpdateClick: () async {
                        Navigator.pushNamed(context, '/updateGame', arguments: {'gameId' : gameId});
                      },
                    ),
                  ],
                ),
              ),
          );
        }
      },
    );
  }

  Future<void> _showDeleteConfirmationDialog(BuildContext context, GameViewModel gameViewModel) async {
    return showDialog<void>(
      context: context,
      builder: (BuildContext context) {
        return DeleteConfirmationDialog(
          onConfirmDelete: () {
            gameViewModel.deleteGame(gameId);
            Navigator.pop(context); // Close the dialog
            Navigator.pop(context); // Close the GameDetailScreen
          },
          onDismiss: () {
            Navigator.pop(context); // Close the dialog
          },
        );
      },
    );
  }
}

class TitleAndGenreColumn extends StatelessWidget {
  final String gameTitle;
  final String gameGenre;

  const TitleAndGenreColumn(
      {super.key, required this.gameTitle, required this.gameGenre});

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        Text(
          gameTitle,
          style: const TextStyle(
            fontWeight: FontWeight.bold,
            fontSize: 24,
            color: Color(0xFFE0E0E2),
          ),
        ),
        const SizedBox(
          height: 5,
        ),
        Text(
          gameGenre,
          style: const TextStyle(
            fontWeight: FontWeight.bold,
            fontSize: 18,
            color: Color(0xFFE0E0E2),
          ),
        ),
      ],
    );
  }
}

class DescriptionRow extends StatelessWidget {
  final String gameDescription;

  DescriptionRow({required this.gameDescription});

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: Card(
        color: const Color(0xFF333437),
        child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: SizedBox(
              width: double.infinity,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  const Text(
                    'Description',
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                      fontSize: 20,
                      color: Color(0xFFE0E0E2),
                    ),
                  ),
                  Text(
                    gameDescription,
                    style: const TextStyle(
                      fontSize: 16,
                      color: Color(0xFFE0E0E2),
                    ),
                  ),
                ],
              ),
            )),
      ),
    );
  }
}

class ProgressAndRatingRow extends StatelessWidget {
  final String gameProgress;
  final String gameRating;

  ProgressAndRatingRow({required this.gameProgress, required this.gameRating});

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Expanded(
          child: Card(
            color: const Color(0xFF333437),
            child: Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  const Text(
                    'Achievements',
                    style: TextStyle(
                      fontSize: 18,
                      color: Color(0xFFE0E0E2),
                    ),
                  ),
                  Text(
                    '$gameProgress%',
                    style: const TextStyle(
                      fontSize: 16,
                      color: Color(0xFFE0E0E2),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ),
        Expanded(
          child: Card(
            color: const Color(0xFF333437),
            child: Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  const Text(
                    'Rating',
                    style: TextStyle(
                      fontSize: 18,
                      color: Color(0xFFE0E0E2),
                    ),
                  ),
                  Text(
                    gameRating,
                    style: const TextStyle(
                      fontSize: 16,
                      color: Color(0xFFE0E0E2),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ),
      ],
    );
  }
}

class HoursPlayedRow extends StatelessWidget {
  final String hoursPlayed;

  HoursPlayedRow({required this.hoursPlayed});

  @override
  Widget build(BuildContext context) {
    return Card(
      color: const Color(0xFF333437),
      child: Padding(
          padding: const EdgeInsets.all(12.0),
          child: SizedBox(
            width: double.infinity,
            child: Text(
              'Hours Played: $hoursPlayed',
              style: const TextStyle(
                fontSize: 16,
                color: Color(0xFFE0E0E2),
              ),
            ),
          )),
    );
  }
}

class ActionButtons extends StatelessWidget {
  final void Function() onDeleteClick;
  final void Function() onUpdateClick;

  ActionButtons({required this.onDeleteClick, required this.onUpdateClick});

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.stretch,
      children: [
        ElevatedButton(
          onPressed: onUpdateClick,
          style: ButtonStyle(
            backgroundColor: MaterialStateProperty.all(const Color(0xFF333437)),
            side: MaterialStateProperty.all(const BorderSide(
              color: Color(0xFFDF921F),
              width: 5,
            )),
          ),
          child:
              const Text('Update', style: TextStyle(color: Color(0xFFFFD232))),
        ),
        const SizedBox(height: 16),
        ElevatedButton(
          onPressed: onDeleteClick,
          style: ButtonStyle(
            backgroundColor: MaterialStateProperty.all(const Color(0xFF333437)),
            side: MaterialStateProperty.all(const BorderSide(
              color: Color(0xFFB00000),
              width: 5,
            )),
          ),
          child:
              const Text('Delete', style: TextStyle(color: Color(0xFFFF1A1A))),
        ),
      ],
    );
  }
}

class DeleteConfirmationDialog extends StatelessWidget {
  final void Function() onConfirmDelete;
  final void Function() onDismiss;

  DeleteConfirmationDialog({
    required this.onConfirmDelete,
    required this.onDismiss,
  });

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: const Text(
        'Confirm delete',
        style: TextStyle(color: Color(0xFFE0E0E2)),
      ),
      content: const Text(
        'Are you sure you want to delete this game?',
        style: TextStyle(color: Color(0xFFE0E0E2)),
      ),
      actions: [
        TextButton(
          onPressed: onDismiss,
          style: TextButton.styleFrom(
            foregroundColor: const Color(0xFFFFD232),
          ),
          child: const Text('No'),
        ),
        TextButton(
          onPressed: onConfirmDelete,
          style: TextButton.styleFrom(
            foregroundColor: const Color(0xFFFF1A1A),
          ),
          child: const Text('Yes'),
        ),
      ],
      backgroundColor: const Color(0xFF333437),
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(10.0),
        side: const BorderSide(color: Color(0xFF212223)),
      ),
    );
  }
}
