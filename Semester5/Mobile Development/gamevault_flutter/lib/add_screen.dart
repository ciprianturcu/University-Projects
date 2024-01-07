import 'dart:async';

import 'package:flutter/material.dart';
import 'package:gamevault_flutter/game.dart';
import 'package:gamevault_flutter/game_viewmodel.dart';
import 'package:gamevault_flutter/validator_functions.dart';
import 'package:logger/logger.dart';
import 'package:provider/provider.dart';

final Logger _log = Logger();

class AddScreen extends StatefulWidget {
  const AddScreen({super.key});

  @override
  State<AddScreen> createState() => _AddScreenState();
}

class _AddScreenState extends State<AddScreen> {
  final TextEditingController _titleController = TextEditingController();
  final TextEditingController _descriptionController = TextEditingController();
  final TextEditingController _genreController = TextEditingController();
  final TextEditingController _progressController = TextEditingController();
  final TextEditingController _ratingController = TextEditingController();
  final TextEditingController _hoursPlayedController = TextEditingController();
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: const Text(
          'Add Game',
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
                  ConfirmFormAddButton(
                    validateFunction: validateInput,
                  )
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
        title: title,
        description: description,
        genre: genre,
        progress: progress,
        rating: rating,
        hoursPlayed: hoursPlayed,
        isSyncedWithServer: 0,
      );
      _addGame(game);
    }
  }

  void _addGame(Game game) async {
    final gameViewModel = Provider.of<GameViewModel>(context, listen : false);
    try {
      if(!gameViewModel.serverStatus) {
        await _showOfflineAddAknowledgementDialog();
      }
      await gameViewModel.addGame(game);
      _showDialog(true);
    } catch (e) {
      _log.e('error adding game in viewmodel : $e');
      _showDialog(false);
    }
  }

  Future<void> _showOfflineAddAknowledgementDialog() async{
    Completer<void> completer = Completer<void>();
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          backgroundColor: const Color(0xFF333437),
          title: const Text(
            'Information',
            style: TextStyle(color: Color(0xFFE0E0E2)),
          ),
          content: const Text(
            'You are offine! Closing the application before a sync with the server is done will lead to this data being lost',
            style: TextStyle(color: Color(0xFFE0E0E2)),
          ),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(context).pop(); // Close the dialog
                completer.complete();
              },
              style: TextButton.styleFrom(
                backgroundColor: const Color(0xFFFFD232),
              ),
              child: const Text('OK'),
            ),
          ],
        );
      },
    );

    await completer.future;
  }

  void _showDialog(bool isSuccess) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          backgroundColor: const Color(0xFF333437),
          title: Text(
            isSuccess ? 'Success' : 'Error',
            style: const TextStyle(color: Color(0xFFE0E0E2)),
          ),
          content: Text(
            isSuccess
                ? 'Game added successfully!'
                : 'Failed to add the game. Please try again.',
            style: const TextStyle(color: Color(0xFFE0E0E2)),
          ),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(context).pop(); // Close the dialog
                if (isSuccess) {
                  Navigator.pop(context); // Go back to the previous screen
                }
              },
              style: TextButton.styleFrom(
                backgroundColor: const Color(0xFFFFD232),
              ),
              child: const Text('OK'),
            ),
          ],
        );
      },
    );
  }
}

class ConfirmFormAddButton extends StatelessWidget {
  final VoidCallback validateFunction;

  const ConfirmFormAddButton({super.key, required this.validateFunction});

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
          validateFunction();
        },
        child: const Text(
          'Add Game',
          style: TextStyle(color: Color(0xFFFFD232)),
        ));
  }
}

class InputFormItem extends StatelessWidget {
  final TextEditingController fieldController;
  final String fieldLabel;
  final TextInputType inputType;
  final String? Function(String?) validatorFunction;

  const InputFormItem(
      {super.key,
      required this.fieldController,
      required this.validatorFunction,
      required this.fieldLabel,
      required this.inputType});

  @override
  Widget build(BuildContext context) {
    return TextFormField(
      cursorColor: const Color(0xFFFFD232),
      style: const TextStyle(color: Color(0xFFE0E0E2)),
      controller: fieldController,
      decoration: InputDecoration(
          labelText: fieldLabel,
          labelStyle: const TextStyle(color: Color(0xFFFFD232)),
          focusedBorder: const OutlineInputBorder(
              borderSide: BorderSide(color: Color(0xFFFFD232))),
          enabledBorder: const OutlineInputBorder(
              borderSide: BorderSide(color: Color(0xFFFFD232)))),
      keyboardType: inputType,
      validator: validatorFunction,
    );
  }
}
