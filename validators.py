import re
from datetime import datetime


def validate_contributor(contributor: str, pattern=None) -> bool:
    """
    A function to validate a contributor.
    :return: True if the contributor is valid, False otherwise.
    """
    # If a pattern is provided, use it to validate the contributor.
    if pattern:
        if pattern.fullmatch(contributor):
            return True
        return False
    # Otherwise, use the default pattern.
    if re.fullmatch(r'[A-Za-z_][A-Za-z_0-9]*', contributor):
        return True
    return False


def validate_commit_hash(commit_hash: str, pattern=None) -> bool:
    """
    A function to validate a commit hash.
    :return: True if the commit hash is valid, False otherwise.
    """
    # If a pattern is provided, use it to validate the commit hash.
    if pattern:
        if pattern.fullmatch(commit_hash):
            return True
        return False
    # Otherwise, use the default pattern.
    if re.fullmatch(r'[a-z0-9]{7}', commit_hash):
        return True
    return False


def validate_date(date: str) -> bool:
    """
    A function to validate a date.
    :return: True if the date is valid, False otherwise.
    """
    try:
        datetime.strptime(date, '%Y-%m-%dT%H:%M:%S')
        return True
    except ValueError:
        return False


def validate_commit(commit: str) -> bool:
    """
    A function to validate a commit.
    :return: True if the commit is valid, False otherwise.
    """
    if not commit:
        return False

    commit_parts = commit.split()
    if len(commit_parts) != 3:
        return False

    contributor, commit_hash, date = commit_parts
    contributor_pattern = re.compile(r'[A-Za-z_][A-Za-z_0-9]*')
    commit_hash_pattern = re.compile(r'[a-z0-9]{7}')

    if not validate_commit_hash(commit_hash, commit_hash_pattern):
        return False
    if not validate_date(date):
        return False
    if not validate_contributor(contributor, contributor_pattern):
        return False
    return True
