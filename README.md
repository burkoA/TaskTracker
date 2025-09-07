# Task Tracker (Java)

A simple command-line task tracker built in **pure Java** without external libraries.
This project is based on the [roadmap.sh/projects/task-tracker](https://roadmap.sh/projects/task-tracker)

## Features
- Add new tasks with description
- List all tasks or by status: **todo**, **in-progress**, **done**
- Mark tasks as in-progress or done
- Delete tasks by ID
- Update tasks description by ID
- Storage in a local file
- Simple JSON-like text format (no external libraries used)

## Commands

```bash
# Add a new task
java TaskTracker add "Buy groceries"
# Output: Task added successfully (ID: 0)

# Updating and deleting tasks
java TaskTracker update 0 "Buy groceries and cook dinner"
java TaskTracker delete 0

# Marking a task as in progress or done
java TaskTracker mark-in-progress 0
java TaskTracker mark-done 0

# Listing all tasks
java TaskTracker list

# Listing tasks by status
java TaskTracker list done
java TaskTracker list todo
java TaskTracker list in-progress

```

## Project Structure
```
TaskTracker/
├── src/
│   ├── Task.java # Represents a single task (id, description, status, createdAt, updatedAt)
│   ├── TaskCommands.java # Business logic for managing tasks and I/O
│   ├── TaskTracker.java # Command-line interface and command parser
│   ├── TaskNotFound.java # Custom exception
│   └── StatusEnum.java # Enum for task statuses: TODO, IN_PROGRESS, DONE
```

## How to Run
```bash
javac TaskTracker.java
java TaskTracker <commands> [args...]
```

## Things to Improve
- Separate I/O logic from business logic in **TaskCommands**
- Optimize **getMaxId()** if the program will run continuously
- Store tasks in a single list: `List<Task> tasks`
- Handle special characters in task descriptions to avoid JSON parsing issues
