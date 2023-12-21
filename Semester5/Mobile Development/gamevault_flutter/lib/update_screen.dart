import 'package:flutter/material.dart';
import 'package:gamevault_flutter/add_screen.dart';
import 'package:gamevault_flutter/db_helper.dart';
import 'package:gamevault_flutter/game.dart';
import 'package:gamevault_flutter/game_viewmodel.dart';
import 'package:gamevault_flutter/validator_functions.dart';
import 'package:logger/logger.dart';
import 'package:provider/provider.dart';

final Logger _log = Logger();

class UpdateScreen extends StatefulWidget {
  const UpdateScreen({super.key});

  @override
  State<UpdateScreen> createState() => _UpdateScreenState();

  static int getGameById(BuildContext context) {
    return (ModalRoute.of(context)!.settings.arguments
        as Map<String, dynamic>)['gameId'];
  }
}

class _UpdateScreenState extends State<UpdateScreen> {
  late DatabaseHelper databaseHelper;
  final TextEditingController _titleController = TextEditingController();
  final TextEditingController _descriptionController = TextEditingController();
  final TextEditingController _genreController = TextEditingController();
  final TextEditingController _progressController = TextEditingController();
  final TextEditingController _ratingController = TextEditingController();
  final TextEditingController _hoursPlayedController = TextEditingController();
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();

  late Game _game;

  @override
  Future<void> didChangeDependencies() async {
    super.didChangeDependencies();
    final int gameId = UpdateScreen.getGameById(context);
    _game = (await Provider.of<GameViewModel>(context).getGameById(gameId))!;
    _titleController.text = _game.title;
    _descriptionController.text = _game.description;
    _genreController.text = _game.genre;
    _progressController.text = _game.progress.toString();
    _ratingController.text = _game.rating.toString();
    _hoursPlayedController.text = _game.hoursPlayed.toString();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: const Text(
          'Update Game',
          style: TextStyle(fontWeight: FontWeight.w700, fontSize: 34.0),
        ),
        backgroundColor: const Color(0xFF212223),
      ),
      backgroundColor: const Color(0xFF212223),
      body: Padding(
          padding: const EdgeInsets.all(16),
          child: SingleChildScrollView(
            child: Form(
              key: _formKey,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: [
                  InputFormItem(
                      fieldController: _titleController,
                      validatorFunction: (value) {
                        return Validator.validateStringFieldInput(value);
                      },
                      fieldLabel: 'Title',
                      inputType: TextInputType.text),
                  const SizedBox(
                    height: 16,
                  ),
                  InputFormItem(
                      fieldController: _descriptionController,
                      validatorFunction: (value) {
                        return Validator.validateStringFieldInput(value);
                      },
                      fieldLabel: 'Description',
                      inputType: TextInputType.text),
                  const SizedBox(
                    height: 16,
                  ),
                  InputFormItem(
                      fieldController: _genreController,
                      validatorFunction: (value) {
                        return Validator.validateStringFieldInput(value);
                      },
                      fieldLabel: 'Genre',
                      inputType: TextInputType.text),
                  const SizedBox(
                    height: 16,
                  ),
                  InputFormItem(
                      fieldController: _progressController,
                      validatorFunction: (value) {
                        return Validator.validateProgressInput(value);
                      },
                      fieldLabel: 'Progress',
                      inputType: TextInputType.number),
                  const SizedBox(
                    height: 16,
                  ),
                  InputFormItem(
                      fieldController: _ratingController,
                      validatorFunction: (value) {
                        return Validator.validateRatingInput(value);
                      },
                      fieldLabel: 'Rating',
                      inputType: TextInputType.number),
                  const SizedBox(
                    height: 16,
                  ),
                  InputFormItem(
                      fieldController: _hoursPlayedController,
                      validatorFunction: (value) {
                        return Validator.validateHoursPlayedInput(value);
                      },
                      fieldLabel: 'Hours Played',
                      inputType: TextInputType.number),
                  const SizedBox(
                    height: 16,
                  ),
                  ConfirmFormUpdateButton(validateFunction: validateInput)
                ],
              ),
            ),
          )),
    );
  }

  void validateInput() {
    if (_formKey.currentState!.validate()) {
      String title = _titleController.text;
      String description = _descriptionController.text;
      String genre = _genreController.text;
      double progress = double.parse(_progressController.text);
      double rating = double.parse(_ratingController.text);
      int hoursPlayed = int.parse(_hoursPlayedController.text);
      Game game = Game(
        id: _game.id,
        title: title,
        description: description,
        genre: genre,
        progress: progress,
        rating: rating,
        hoursPlayed: hoursPlayed,
      );
      _updateGameInDatabase(game);
    }
  }

  void _updateGameInDatabase(Game game) async {
    final gameViewModel = Provider.of<GameViewModel>(context, listen: false);
    try {
      await gameViewModel.updateGame(game);
      _showSuccessDialog('Game updated successfully!');
    } catch (e) {
      _showErrorDialog('Failed to update the game. Please try again.');
    }
  }

  void _showSuccessDialog(String message) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Success'),
          content: Text(message),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(context).pop(); // Close the dialog
                Navigator.pop(context); // Go back to the previous screen
              },
              child: const Text('OK'),
            ),
          ],
        );
      },
    );
  }

  void _showErrorDialog(String message) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Error'),
          content: Text(message),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(context).pop(); // Close the dialog
              },
              child: const Text('OK'),
            ),
          ],
        );
      },
    );
  }
}

class ConfirmFormUpdateButton extends StatelessWidget {
  final VoidCallback validateFunction;

  const ConfirmFormUpdateButton({super.key, required this.validateFunction});

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      style: ElevatedButton.styleFrom(
        backgroundColor: const Color(0xFF35363A),
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(10.0),
          side: const BorderSide(color: Color(0xFFFFD232)),
        ),
      ),
      onPressed: () {
        validateFunction;
      },
      child: const Text(
        'Update Game',
        style: TextStyle(color: Color(0xFFFFD232)),
      ),
    );
  }
}
