#!/usr/bin/python3

import sys
import os
import subprocess

import traceback

crnt_dir = os.path.abspath(os.path.dirname(sys.argv[0]))

# os.system(os.path.join(crnt_dir, "script.sh"))

class RunException(Exception):
    def __init__(self, code, msg):
        self.code = code
        self.msg = msg

    def __str__(self):
        return "RunException({}, {})".format(self.code, self.msg)


def runex(script_name):
    res = subprocess.run([script_name], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    if res.returncode != 0:
        raise RunException(res.returncode, "stdout={}, stderr={}".format(res.stdout, res.stderr))

def main(args):
    print("Script is running now")

    script_name = os.path.join(crnt_dir, "script.sh")
    runex(script_name)

    runex(another_script_name)


if __name__ == '__main__':
    try:
        main(sys.argv[1:])
    except Exception as e:
        print(traceback.format_exc())
        print("Got error: " + str(e))


