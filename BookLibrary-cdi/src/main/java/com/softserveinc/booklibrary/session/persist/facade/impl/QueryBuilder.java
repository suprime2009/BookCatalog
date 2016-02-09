package com.softserveinc.booklibrary.session.persist.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.action.helper.DataTableSearchHolder;

/**
 * This abstract class is a common query builder for {@code Book Catalog API}
 * DataTables. The class provides building queries by parts: (SELECT, FROM,
 * WHERE, GROUP BY, HAVING, ORDER BY). The class can build a query for getting
 * all needed data for dataTable and query for getting count of found objects.
 *
 */
public abstract class QueryBuilder {

	private static Logger log = LoggerFactory.getLogger(QueryBuilder.class);

	protected StringBuilder sbForDataTable;
	protected DataTableSearchHolder dataTableSearchHolder;

	/**
	 * The method returns fully constructed query for getting objects in
	 * according for dataTable requirements. Requirements for dataTable
	 * described by {@link DataTableSearchHolder} instance, that passed by
	 * parameter.
	 * 
	 * @param dataTableSearchHolder
	 *            DTO that describe requirements for dataTable.
	 * @return query for getting objects for dataTable.
	 */
	public String getObjectsQuery(DataTableSearchHolder dataTableSearchHolder) {
		sbForDataTable = new StringBuilder();
		this.dataTableSearchHolder = dataTableSearchHolder;
		appendQueryPartSelectObjects();
		appendQueryPartFrom();
		if (!dataTableSearchHolder.getFilterValues().isEmpty()) {
			appendQueryPartWhere();
		}
		appendQueryPartGroupBy();
		if (!dataTableSearchHolder.getFilterValues().isEmpty()) {
			appendQueryPartHaving();
		}
		appendQueryPartOrderBy();
		log.info("Query for getting Books for dataTable created == {}", sbForDataTable.toString());
		return sbForDataTable.toString();

	}

	/**
	 * The method returns fully constructed query for getting count of objects
	 * in according for dataTable requirements. Requirements for dataTable
	 * described by {@link DataTableSearchHolder} instance, that passed by
	 * parameter.
	 * 
	 * @param dataTableSearchHolder
	 *            DTO that describe requirements for dataTable.
	 * @return query for getting count of found objects.
	 */
	public String getCountObjectsQuery(DataTableSearchHolder dataTableSearchHolder) {
		sbForDataTable = new StringBuilder();
		this.dataTableSearchHolder = dataTableSearchHolder;
		appendQueryPartSelectCountObjects();
		appendQueryPartFrom();
		if (!dataTableSearchHolder.getFilterValues().isEmpty()) {
			appendQueryPartWhere();
		}
		appendQueryPartGroupBy();
		if (!dataTableSearchHolder.getFilterValues().isEmpty()) {
			appendQueryPartHaving();
		}
		sbForDataTable.append(')');
		log.info("Query for getting Books for dataTable created == {}", sbForDataTable.toString());
		return sbForDataTable.toString();
	}

	/**
	 * The method appends for query SQL attribute {@code SELECT} and values,
	 * that needed for DataTable.
	 */
	protected abstract void appendQueryPartSelectObjects();

	/**
	 * The method appends for query SQL attribute {@code SELECT} and
	 * {@code COUNT} by field. The method must build Select part of a query,
	 * which should return a count of the objects.
	 */
	protected abstract void appendQueryPartSelectCountObjects();

	/**
	 * The method appends for query SQL attribute {@code FROM} with entity name
	 * or mapping between entities (example {@code JOIN}).
	 */
	protected abstract void appendQueryPartFrom();

	/**
	 * The method appends for query filtering attribute {@code WHERE} with
	 * fields and values for filtering.
	 * 
	 */
	protected abstract void appendQueryPartWhere();

	/**
	 * The method appends for query grouping attribute {@code BROUP BY} with
	 * values for grouping.
	 * 
	 */
	protected abstract void appendQueryPartGroupBy();

	/**
	 * The method appends for query filtering attribute {@code HAVING} with
	 * fields and values to filtering.
	 * 
	 */
	protected abstract void appendQueryPartHaving();

	/**
	 * Method appends for query sorting attribute {@code ORDER BY} with fields
	 * and Orders for sorting.
	 */
	protected abstract void appendQueryPartOrderBy();
	
	static class SqlConstantsHolder {
		public static final String SELECT ="SELECT ";
		public static final String FROM =" FROM ";
		public static final String WHERE =" WHERE ";
		public static final String JOIN =" JOIN ";
		public static final String LEFT_JOIN =" LEFT JOIN ";
		public static final String RIGHT_JOIN =" RIGHT JOIN ";
		public static final String AS ="AS";
		public static final String GROUP_BY =" GROUP BY ";
		public static final String ORDER_BY =" ORDER BY ";
		public static final String AND =" AND ";
		public static final String OR =" OR ";
		public static final String IN =" IN ";
		public static final String LIKE =" LIKE ";
		public static final String DESC =" DESC ";
		public static final String ASC =" ASC ";
		public static final String AVG =" AVG";
		public static final String COUNT =" COUNT";
		public static final String HAVING =" HAVING ";
		public static final String FLOOR =" FLOOR";
		public static final String FORMAT =" FORMAT";
		public static final String DISTINCT =" DISTINCT ";
		public static final String ROUND =" ROUND ";
		
		//Personal alias for Book, Author, Review and rating
		public static final String B =" b";
		public static final String A =" a";
		public static final String R =" r";
		public static final String RAT =" rat ";
		public static final String COMMA = ", ";
		public static final String EQUAL = " = ";
		
		//String templates
		public static final String LIKE_TEMPLATE = " LIKE '%s%%' ";
		public static final String AGREGATE_FUNC_TEMPLATE = " %s(%s.%s) ";
		public static final String AGREGATE_FUNC_DISTINCT_TEMPLATE = " %s( DISTINCT %s) ";
		public static final String FIELD_TEMPLATE = " %s.%s ";
		public static final String FLOOR_TEMPLATE = " FLOOR(AVG(%s.%s)) ";
		public static final String ROUND_AVG_TEMPLATE_TO_TWO_DIGITS = " ROUND(AVG(%s.%s), 2) ";
	}

}
