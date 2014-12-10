db_path = "list.db"
db_lines = []
db_tasks = []
db_status = "not read"

class Task(object):
    def __init__(self, filedby="None", project="None", datefiled="None"):
        self.filedby = filedby
        self.project = project
        self.datefiled = datefiled

    def __getattr__(self, name):
        raise AttributeError("Sorry, this Task object doesn't have a " + name + " attribute.")

    def __setattr__(self, name, value):
        if name in ['filedby', 'project', 'datefiled']:
            self.__dict__[name] = value
        else:
            raise AttributeError("Sorry, you can't set an " + name + " attribute with value " + value + " for this Task object.")

def make_task_from_db(line):
    return Task()

def read_in_database():
    print("Reading in database...")
    with open(db_path, 'r') as database:
        db_lines = database.readlines()
        if not db_lines[0].startswith("Database Status: "):
            print("BAD FORMAT.")
        else:
            db_status = db_lines[0][17:]
            print(db_lines[0])
        counter = 0
        for line in db_lines[1:]:
            db_tasks.append(line)
            counter += 1
            if(counter % 5) == 0:
                print("Read " + str(counter) + " lines.")
        print("Read " + str(counter) + " lines.\nDone reading in database.")

def user_quit():
    user_input = input("Are you sure you want to quit (y/n)? ")
    return user_input.startswith("y")

def get_help():
    print('Type q to quit, a to add a task, l to list all the tasks, and r to read in all the tasks from the database.')

menu = {'r':read_in_database, 'h':get_help}
def main_loop():
    print(("*" * 29) + "\n*** YET ANOTHER TASK LIST ***\n" + ("*" * 29) + "\n")
    read_in_database()
    done = False
    get_help()
    while not done:
        user_input = input("\nuser> ").lower().strip()
        if len(user_input) != 1:
            print('Please enter only the commands listed.')
            get_help()
        if user_input == 'q':
            done = user_quit()
        else:
            menu[user_input]()

main_loop()
