package ru.nukolay.stupnikov.animelist.data

import ru.nukolay.stupnikov.animelist.data.repository.DatabaseRepository
import ru.nukolay.stupnikov.animelist.data.repository.NetworkRepository

interface DataManager: NetworkRepository, DatabaseRepository