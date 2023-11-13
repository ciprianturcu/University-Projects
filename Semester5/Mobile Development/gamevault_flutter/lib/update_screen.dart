import 'package:flutter/material.dart';
import 'package:gamevault_flutter/db_helper.dart';
import 'package:gamevault_flutter/game.dart';

class UpdateScreen extends StatefulWidget {
  final Game game;

  UpdateScreen({required this.game});

  @override
  State<UpdateScreen> createState() => _UpdateScreenState();
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

  @override
  void initState() {
    super.initState();
    databaseHelper = DatabaseHelper();
    _fetchAndUpdateFields();
  }

  Future<void> _fetchAndUpdateFields() async {
    _titleController.text = widget.game.title;
    _descriptionController.text = widget.game.description;
    _genreController.text = widget.game.genre;
    _progressController.text = widget.game.progress.toString();
    _ratingController.text = widget.game.rating.toString();
    _hoursPlayedController.text = widget.game.hoursPlayed.toString();
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
                  TextFormField(
                    controller: _titleController,
                    cursorColor: const Color(0xFFFFD232),
                    style: const TextStyle(color: Color(0xFFE0E0E2)),
                    decoration: const InputDecoration(
                      labelText: 'Title',
                      labelStyle: TextStyle(color: Color(0xFFFFD232)),
                      focusedBorder: OutlineInputBorder(
                          borderSide: BorderSide(color: Color(0xFFFFD232))),
                      enabledBorder: OutlineInputBorder(
                          borderSide: BorderSide(color: Color(0xFFFFD232))),
                    ),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please enter a title';
                      }
                      return null;
                    },
                  ),
                  const SizedBox(
                    height: 16,
                  ),
                  TextFormField(
                    cursorColor: const Color(0xFFFFD232),
                    controller: _descriptionController,
                    style: const TextStyle(color: Color(0xFFE0E0E2)),
                    decoration: const InputDecoration(
                        labelText: 'Description',
                        labelStyle: TextStyle(color: Color(0xFFFFD232)),
                        focusedBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232))),
                        enabledBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232)))),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please enter a description';
                      }
                      return null;
                    },
                  ),
                  const SizedBox(
                    height: 16,
                  ),
                  TextFormField(
                    cursorColor: const Color(0xFFFFD232),
                    controller: _genreController,
                    style: const TextStyle(color: Color(0xFFE0E0E2)),
                    decoration: const InputDecoration(
                        labelText: 'Genre',
                        labelStyle: TextStyle(color: Color(0xFFFFD232)),
                        focusedBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232))),
                        enabledBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232)))),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please enter a genre';
                      }
                      return null;
                    },
                  ),
                  const SizedBox(
                    height: 16,
                  ),
                  TextFormField(
                    cursorColor: const Color(0xFFFFD232),
                    controller: _progressController,
                    style: const TextStyle(color: Color(0xFFE0E0E2)),
                    decoration: const InputDecoration(
                        labelText: 'Progress',
                        labelStyle: TextStyle(color: Color(0xFFFFD232)),
                        focusedBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232))),
                        enabledBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232)))),
                    keyboardType: TextInputType.number,
                    validator: (value) {
                      if (value == null || double.tryParse(value) == null) {
                        return 'Please enter the progress (between 0-100)';
                      }
                      if (double.tryParse(value)! < 0 ||
                          double.tryParse(value)! > 100) {
                        return 'Not in the range of (0-100)';
                      }
                      return null;
                    },
                  ),
                  const SizedBox(
                    height: 16,
                  ),
                  TextFormField(
                    cursorColor: const Color(0xFFFFD232),
                    controller: _ratingController,
                    style: const TextStyle(color: Color(0xFFE0E0E2)),
                    decoration: const InputDecoration(
                        labelText: 'Rating',
                        labelStyle: TextStyle(color: Color(0xFFFFD232)),
                        focusedBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232))),
                        enabledBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232)))),
                    keyboardType: TextInputType.number,
                    validator: (value) {
                      if (value == null || double.tryParse(value) == null) {
                        return 'Please enter the rating (between 0-5)';
                      }
                      if (double.tryParse(value)! < 0 ||
                          double.tryParse(value)! > 5) {
                        return 'Not in the range of (0-5)';
                      }
                      return null;
                    },
                  ),
                  const SizedBox(
                    height: 16,
                  ),
                  TextFormField(
                    cursorColor: const Color(0xFFFFD232),
                    controller: _hoursPlayedController,
                    style: const TextStyle(color: Color(0xFFE0E0E2)),
                    decoration: const InputDecoration(
                        labelText: 'Hours Played',
                        labelStyle: TextStyle(color: Color(0xFFFFD232)),
                        focusedBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232))),
                        enabledBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Color(0xFFFFD232)))),
                    keyboardType: TextInputType.number,
                    validator: (value) {
                      if (value == null || double.tryParse(value) == null) {
                        return 'Please enter the number of hours played';
                      }
                      if (int.tryParse(value)! < 0) {
                        return 'Should be greater than 0';
                      }
                      return null;
                    },
                  ),
                  const SizedBox(
                    height: 16,
                  ),
                  ElevatedButton(
                    style: ElevatedButton.styleFrom(
                      backgroundColor: const Color(0xFF35363A),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(10.0),
                        side: const BorderSide(color: Color(0xFFFFD232)),
                      ),
                    ),
                    onPressed: () {
                      if (_formKey.currentState!.validate()) {
                        String title = _titleController.text;
                        String description = _descriptionController.text;
                        String genre = _genreController.text;
                        double progress =
                            double.parse(_progressController.text);
                        double rating = double.parse(_ratingController.text);
                        int hoursPlayed =
                            int.parse(_hoursPlayedController.text);
                        Game game = Game(
                          id: widget.game.id,
                          title: title,
                          description: description,
                          genre: genre,
                          progress: progress,
                          rating: rating,
                          hoursPlayed: hoursPlayed,
                        );

                        _updateGameInDatabase(game);
                      }
                    },
                    child: const Text(
                      'Update Game',
                      style: TextStyle(color: Color(0xFFFFD232)),
                    ),
                  ),
                ],
              ),
            ),
          )),
    );
  }

  void _updateGameInDatabase(Game game) async {
    int result = await databaseHelper.updateGame(game);

    if (result != 0) {
      _showSuccessDialog('Game updated successfully!');
    } else {
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
