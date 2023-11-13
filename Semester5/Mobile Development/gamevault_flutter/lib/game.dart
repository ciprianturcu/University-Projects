class Game {
  int? id;
  String title;
  String description;
  String genre;
  double progress;
  double rating;
  int hoursPlayed;

  Game({
    this.id,
    required this.title,
    required this.description,
    required this.genre,
    required this.progress,
    required this.rating,
    required this.hoursPlayed,
  });

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'title': title,
      'description': description,
      'genre': genre,
      'progress': progress,
      'rating': rating,
      'hoursPlayed': hoursPlayed,
    };
  }

  factory Game.fromMap(Map<String, dynamic> map) {
    return Game(
      id: map['id'],
      title: map['title'],
      description: map['description'],
      genre: map['genre'],
      progress: map['progress'],
      rating: map['rating'],
      hoursPlayed: map['hoursPlayed'],
    );
  }
  
}