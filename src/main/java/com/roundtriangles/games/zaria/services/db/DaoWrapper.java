package com.roundtriangles.games.zaria.services.db;

import com.j256.ormlite.dao.Dao;

public class DaoWrapper<T> {

    private static final int CACHE_SIZE = 1000;

    public Dao<T, Integer> dao;
    public Class<T> clazz;

    //    /**
    //     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our Question class. It will
    //     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
    //     */
    //    public Dao<T, Integer> getDao() {
    //        try {
    //            if (dao == null) {
    //                dao = getDao(clazz);
    //            }
    //        } catch (SQLException e) {
    //            throw new GdxRuntimeException(e);
    //        }
    //        return dao;
    //    }
    //
    //    /**
    //     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our Question class. It will
    //     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
    //     */
    //    public RuntimeExceptionDao<Question, Integer> getQuestionDao() {
    //        if (runtimeExceptionDao == null) {
    //            runtimeExceptionDao = getRuntimeExceptionDao(Question.class);
    //            runtimeExceptionDao.setObjectCache(true);
    //            runtimeExceptionDao.setObjectCache(new LruObjectCache(CACHE_SIZE));
    //        }
    //        return questionRuntimeDao;
    //    }
    //
    //    public OrmliteCursorLoader<Question> getQuestionLoader() {
    //        if (questionLoader == null) {
    //            questionLoader = new OrmliteCursorLoader<Question>(context, getDao());
    //        }
    //        return questionLoader;
    //    }

}
